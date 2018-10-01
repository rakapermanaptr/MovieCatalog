package com.example.rakapermanaputra.moviewcatalog.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.activity.MainActivity;
import com.example.rakapermanaputra.moviewcatalog.adapter.MorePopularAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.SearchAdapter;
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
public class SearchFragment extends Fragment {

    private List<Result> movieList;
    private SearchAdapter adapter;
    private String search;
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

        initView();

        if (search == null) {
            imgSearch.setVisibility(View.VISIBLE);
        } else {
            imgSearch.setVisibility(View.GONE);
            initView();
        }

        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList("search");
            adapter = new SearchAdapter(getActivity());
            adapter.setMovieItems(SearchFragment.this.movieList);
            recyclerViewSearch.setAdapter(adapter);
            imgSearch.setVisibility(View.GONE);

            if (movieList == null) {
                getSearch();
                imgSearch.setVisibility(View.GONE);
            } else {
                adapter = new SearchAdapter(getActivity());
                adapter.setMovieItems(SearchFragment.this.movieList);
                recyclerViewSearch.setAdapter(adapter);
                imgSearch.setVisibility(View.GONE);
            }
        } else {
            getSearch();

        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (movieList == null) {
            getSearch();

        } else {
            outState.putParcelableArrayList("search", new ArrayList<>(movieList));
        }
    }

    private void initView() {
        recyclerViewSearch.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerViewSearch.setLayoutManager(layoutManager);
    }


    private void getSearch() {
        boolean isEmptyFields = false;
        if (TextUtils.isEmpty(search)) {
            isEmptyFields = true;

        }
        if (!isEmptyFields) {
            Toast.makeText(getContext(), search, Toast.LENGTH_SHORT).show();
            ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
            Call<MovieItems> call = service.getMovie(search);
            call.enqueue(new Callback<MovieItems>() {
                @Override
                public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
                    MovieItems movieItems = response.body();
                    movieList = movieItems.getResults();
                    for (int i = 0; i < SearchFragment.this.movieList.size(); i++) {
                        Result items = SearchFragment.this.movieList.get(i);

                        Log.i("Search Fragment ", "onResponse: movie title : " + items.getTitle());
                    }
                    adapter = new SearchAdapter(getContext());
                    adapter.setMovieItems(SearchFragment.this.movieList);
                    recyclerViewSearch.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<MovieItems> call, Throwable t) {

                }
            });
        }
    }

}
