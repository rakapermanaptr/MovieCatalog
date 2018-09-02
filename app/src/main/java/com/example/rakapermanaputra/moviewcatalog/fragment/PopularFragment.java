package com.example.rakapermanaputra.moviewcatalog.fragment;


import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.adapter.MorePopularAdapter;
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
public class PopularFragment extends Fragment {

    private static final String TAG = "request ";
    @BindView(R.id.recyclerViewPopular)
    RecyclerView rvMorePopular;
    private List<Result> movieList;
    private MorePopularAdapter morePopularAdapter;



    public PopularFragment() {
        // Required empty public constructor

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        ButterKnife.bind(this, view);
        rvMorePopular.setHasFixedSize(true);

        getPopular();

        return view;
    }

    private void getPopular() {
        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<MovieItems> call = service.getPopular();
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
                rvMorePopular.setLayoutManager(linearLayoutManager);
                morePopularAdapter = new MorePopularAdapter(getActivity());
                morePopularAdapter.setMovieItems(PopularFragment.this.movieList);
                rvMorePopular.setAdapter(morePopularAdapter);
            }

            @Override
            public void onFailure(Call<MovieItems> call, Throwable t) {

            }
        });
    }




}
