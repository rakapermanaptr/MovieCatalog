package com.example.rakapermanaputra.moviewcatalog.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.activity.MainActivity;
import com.example.rakapermanaputra.moviewcatalog.adapter.MoreNowPlayingAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.MorePopularAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.NowPlayingAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.PopularAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.SearchAdapter;
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
public class SearchFragment extends Fragment {

    private ArrayList<MovieItems> movieItems;
    private SearchAdapter adapter;
    private String search ;
    @BindView(R.id.recyclerViewSearch)
    RecyclerView recyclerViewSearch;
    @BindView(R.id.imgSearch)
    ImageView imgSearch;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        MainActivity mainActivity = (MainActivity) getActivity();
        search = mainActivity.dataSearch();
        ButterKnife.bind(this, view);

        if (search == null) {
            imgSearch.setVisibility(View.VISIBLE);
        } else {
            imgSearch.setVisibility(View.GONE);
            getSearch();
        }

        return view;
    }


    private void getSearch() {
        Toast.makeText(getContext(), search, Toast.LENGTH_SHORT).show();
        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<JSONResponse> call = service.getMovie(search);
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                movieItems = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                for (int i = 0; i < movieItems.size(); i++) {
                    MovieItems items = movieItems.get(i);

                    Log.i("Search Fragment ", "onResponse: movie title : " + items.getTitle());
                }
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
                recyclerViewSearch.setLayoutManager(linearLayoutManager);
                adapter = new SearchAdapter(getContext());
                adapter.setMovieItems(movieItems);
                recyclerViewSearch.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }

}
