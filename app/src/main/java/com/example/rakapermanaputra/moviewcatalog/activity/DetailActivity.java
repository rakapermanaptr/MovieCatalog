package com.example.rakapermanaputra.moviewcatalog.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.adapter.RecommendationAdapter;
import com.example.rakapermanaputra.moviewcatalog.database.MovieHelper;
import com.example.rakapermanaputra.moviewcatalog.model.MovieItems;
import com.example.rakapermanaputra.moviewcatalog.model.Result;
import com.example.rakapermanaputra.moviewcatalog.network.ApiService;
import com.example.rakapermanaputra.moviewcatalog.network.RetrofitClientInstance;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import com.squareup.picasso.Picasso;

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

    public static String EXTRA_DATA = "extra_data";
    public static String EXTRA_POSITION = "extra_position";
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int RESULT_DELETE = 301;

    private Result items;
    private int position;
    private MovieHelper movieHelper;

    List<Result> movieList;
    RecommendationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        seDataIntent();

        movieHelper = new MovieHelper(this);
        movieHelper.open();

        items = getIntent().getParcelableExtra(EXTRA_DATA);

        btnFavorite.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                Toast.makeText(DetailActivity.this, items.getTitle() + " Added to Favorite", Toast.LENGTH_SHORT).show();

                String title = tvTitle.getText().toString().trim();
                String release_date = tvReleaseDate.getText().toString().trim();
                String overview = tvOverview.getText().toString().trim();
                String poster_path = items.getPosterPath();

                Log.i("TEST  ", "getPosterPath : " + poster_path);

                Result items = new Result();
                items.setTitle(title);
                items.setReleaseDate(release_date);
                items.setOverview(overview);
                items.setPosterPath(poster_path);

                movieHelper.insert(items);
                setResult(RESULT_ADD);
                finish();
            }
        });

    }


    private void seDataIntent() {

        Result result = getIntent().getParcelableExtra(EXTRA_DATA);
        String title = result.getTitle();
        tvTitle.setText(title);
        Integer id = result.getId();
        String idMovie = String.valueOf(id);
        String release_date = result.getReleaseDate();
        tvReleaseDate.setText("Release Date \n" + release_date);
        String overview = result.getOverview();
        tvOverview.setText(overview);
        Float rate = result.getVoteAverage();
        tvVoteAverage.setText(String.valueOf(rate));

        String backdrop_img = result.getBackdropPath();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (movieHelper != null) {
            movieHelper.close();
        }
    }

}
