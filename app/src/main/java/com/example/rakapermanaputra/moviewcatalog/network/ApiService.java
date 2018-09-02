package com.example.rakapermanaputra.moviewcatalog.network;

import com.example.rakapermanaputra.moviewcatalog.BuildConfig;
import com.example.rakapermanaputra.moviewcatalog.model.MovieItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {



    @GET("movie/popular" + BuildConfig.API_KEY)
    Call<MovieItems> getPopular();

    @GET("movie/now_playing" + BuildConfig.API_KEY)
    Call<MovieItems> getNowPlaying();

    @GET("movie/upcoming" + BuildConfig.API_KEY)
    Call<MovieItems> getUpcoming();

    @GET("movie/{movie_id}/recommendations" + BuildConfig.API_KEY)
    Call<MovieItems> getRecommend(@Path("movie_id") String id);

    @GET("search/movie" + BuildConfig.API_KEY)
    Call<MovieItems> getMovie(@Query("query") String query);


}
