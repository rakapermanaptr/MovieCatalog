package com.example.rakapermanaputra.moviewcatalog.network;

import android.os.Build;

import com.example.rakapermanaputra.moviewcatalog.BuildConfig;
import com.example.rakapermanaputra.moviewcatalog.model.JSONResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {



    @GET("movie/popular?api_key=" + BuildConfig.API_KEY)
    Call<JSONResponse> getPopular();

    @GET("movie/now_playing?api_key=" + BuildConfig.API_KEY)
    Call<JSONResponse> getNowPlaying();

    @GET("movie/upcoming?api_key=" + BuildConfig.API_KEY)
    Call<JSONResponse> getUpcoming();

    @GET("movie/{movie_id}/recommendations?api_key=" + BuildConfig.API_KEY)
    Call<JSONResponse> getRecommend(@Path("movie_id") String id);

    @GET("search/movie?api_key=" + BuildConfig.API_KEY)
    Call<JSONResponse> getMovie(@Query("query") String query);


}
