package com.example.rakapermanaputra.moviewcatalog.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.adapter.RecommendationAdapter;
import com.example.rakapermanaputra.moviewcatalog.model.MovieItems;
import com.example.rakapermanaputra.moviewcatalog.model.Result;
import com.example.rakapermanaputra.moviewcatalog.network.ApiService;
import com.example.rakapermanaputra.moviewcatalog.network.RetrofitClientInstance;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_release_date)
    TextView tvReleaseDate;
    @BindView(R.id.tv_vote_average)
    TextView tvVoteAverage;
    @BindView(R.id.overview_detail)
    TextView tvOverview;
    @BindView(R.id.img_backdrop)
    ImageView imgBackdrop;
    @BindView(R.id.recyclerViewRecommendation)
    RecyclerView rvRecommendations;
    @BindView(R.id.btn_favorite)
    MaterialFavoriteButton btnFavorite;
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_DATE = "release_date";
    public static String EXTRA_OVERVIEW = "overview";
    List<Result> movieList;
    RecommendationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        seDataIntent();


        btnFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                btnFavorite.setFavorite(true);
            }
        });

    }


    private void seDataIntent() {
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        tvTitle.setText(title);
        Integer id = getIntent().getIntExtra("id", 0);
        String idMovie = String.valueOf(id);
        String release_date = getIntent().getStringExtra(EXTRA_DATE);
        tvReleaseDate.setText("Release Date \n" + release_date);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        tvOverview.setText(overview);
        Float rate = getIntent().getFloatExtra("vote_average", 0);
        tvVoteAverage.setText(String.valueOf(rate));

        String backdrop_img = getIntent().getStringExtra("backdrop_path");
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + backdrop_img)
                .into(imgBackdrop);


        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<MovieItems> call = service.getRecommend(idMovie);
        call.enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
                MovieItems movieItems = response.body();
                movieList = movieItems.getResults();

                for (int i = 0; i < DetailActivity.this.movieList.size(); i++) {
                    Result items = DetailActivity.this.movieList.get(i);

                    Log.i("Detail Activity  ", "recommendation: " + items.getTitle());
                }
                rvRecommendations.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                rvRecommendations.setLayoutManager(linearLayoutManager);
                adapter = new RecommendationAdapter(getApplicationContext());
                adapter.setMovieItems(DetailActivity.this.movieList);
                rvRecommendations.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<MovieItems> call, Throwable t) {

            }
        });

    }

}
