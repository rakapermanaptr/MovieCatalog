package com.example.rakapermanaputra.moviewcatalog.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.adapter.MoreNowPlayingAdapter;
import com.example.rakapermanaputra.moviewcatalog.model.MovieItems;
import com.example.rakapermanaputra.moviewcatalog.model.Result;
import com.example.rakapermanaputra.moviewcatalog.network.ApiService;
import com.example.rakapermanaputra.moviewcatalog.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends android.support.v4.app.Fragment {
    private static final String TAG = "request ";
    @BindView(R.id.recyclerViewNowPlaying)
    RecyclerView rvMoreNowPlaying;
    private List<Result> movieList;
    private MoreNowPlayingAdapter moreNowPlayingAdapter;


    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        ButterKnife.bind(this, view);
        rvMoreNowPlaying.setHasFixedSize(true);

        getNowPlaying();

        return view;
    }

    private void getNowPlaying() {
        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<MovieItems> call = service.getNowPlaying();
        call.enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
                MovieItems movieItems = response.body();
                movieList = movieItems.getResults();

                for (int i = 0; i < movieList.size(); i++) {
                    Result result = movieList.get(i);

                    Log.i(TAG, "onResponse: " + "get title : " + result.getTitle());
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                rvMoreNowPlaying.setLayoutManager(linearLayoutManager);
                moreNowPlayingAdapter = new MoreNowPlayingAdapter(getActivity());
                moreNowPlayingAdapter.setMovieItems(NowPlayingFragment.this.movieList);
                rvMoreNowPlaying.setAdapter(moreNowPlayingAdapter);
            }

            @Override
            public void onFailure(Call<MovieItems> call, Throwable t) {

            }
        });
    }

}
