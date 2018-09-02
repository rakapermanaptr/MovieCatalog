package com.example.rakapermanaputra.moviewcatalog.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.adapter.RecommendationAdapter;
import com.example.rakapermanaputra.moviewcatalog.model.JSONResponse;
import com.example.rakapermanaputra.moviewcatalog.model.MovieItems;
import com.example.rakapermanaputra.moviewcatalog.network.ApiService;
import com.example.rakapermanaputra.moviewcatalog.network.RetrofitClientInstance;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;

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
    @BindView(R.id.recyclerViewRecommendation) RecyclerView rvRecommendations;
    public static String EXTRA_TITLE = "title";
    public static String EXTRA_DATE = "release_date";
    public static String EXTRA_OVERVIEW = "overview";
    ArrayList<MovieItems> movieItems;
    RecommendationAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        seDataIntent();

    }


    private void seDataIntent() {
        String title = getIntent().getStringExtra(EXTRA_TITLE);
        tvTitle.setText(title);
        String id = getIntent().getStringExtra("id");
        String release_date = getIntent().getStringExtra(EXTRA_DATE);
        tvReleaseDate.setText("Release Date \n"+release_date);
        String overview = getIntent().getStringExtra(EXTRA_OVERVIEW);
        tvOverview.setText(overview);
        Double rate = getIntent().getDoubleExtra("vote_average", 0);
        tvVoteAverage.setText(String.valueOf(rate));

        String backdrop_img = getIntent().getStringExtra("backdrop_path");
        Picasso.get()
                .load("http://image.tmdb.org/t/p/w500" + backdrop_img)
                .into(imgBackdrop);


        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<JSONResponse> call = service.getRecommend(id);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                movieItems = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                for (int i = 0; i < movieItems.size(); i++) {
                    MovieItems items = movieItems.get(i);

                    Log.i("Detail Activity  ", "recommendation: " + items.getTitle());
                }
                rvRecommendations.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                rvRecommendations.setLayoutManager(linearLayoutManager);
                adapter = new RecommendationAdapter(getApplicationContext());
                adapter.setMovieItems(movieItems);
                rvRecommendations.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });

    }

}
