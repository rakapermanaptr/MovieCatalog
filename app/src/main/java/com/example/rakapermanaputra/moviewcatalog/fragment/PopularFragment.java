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
import com.example.rakapermanaputra.moviewcatalog.model.JSONResponse;
import com.example.rakapermanaputra.moviewcatalog.model.MovieItems;
import com.example.rakapermanaputra.moviewcatalog.network.ApiService;
import com.example.rakapermanaputra.moviewcatalog.network.RetrofitClientInstance;

import java.util.ArrayList;
import java.util.Arrays;

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
    private ArrayList<MovieItems> movieItems;
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
        Call<JSONResponse> call = service.getPopular();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                movieItems = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                for (int i = 0; i < movieItems.size(); i++) {
                    MovieItems items = movieItems.get(i);

                    Log.i(TAG, "onResponse: movie id : " + items.getId());
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                rvMorePopular.setLayoutManager(linearLayoutManager);
                morePopularAdapter = new MorePopularAdapter(getActivity());
                morePopularAdapter.setMovieItems(movieItems);
                rvMorePopular.setAdapter(morePopularAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }




}
