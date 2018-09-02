package com.example.rakapermanaputra.moviewcatalog.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.adapter.NowPlayingAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.PopularAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.UpcomingAdapter;
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
public class HomeFragment extends Fragment {
    private static final String TAG = "Request ";
    @BindView(R.id.tv_more_popular)
    TextView tvMorePopular;
    @BindView(R.id.tv_more_now_playing)
    TextView tvMoreNowPlaying;
    @BindView(R.id.tv_more_upcoming)
    TextView tvMoreUpcoming;
    @BindView(R.id.rv_popular)
    RecyclerView rvPopular;
    @BindView(R.id.rv_nowPlaying)
    RecyclerView rvNowPlaying;
    @BindView(R.id.rv_upcoming)
    RecyclerView rvUpcoming;
    private ArrayList<MovieItems> movieItems;
    private PopularAdapter popularAdapter;
    private NowPlayingAdapter nowPlayingAdapter;
    private UpcomingAdapter upcomingAdapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);


        tvMorePopular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.more_popular, Toast.LENGTH_SHORT).show();
                Fragment fragmentPopular = new PopularFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragmentPopular).addToBackStack(null).commit();  }
        });

        tvMoreNowPlaying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.more_now_playing, Toast.LENGTH_SHORT).show();
                Fragment fragmentNowPlaying = new NowPlayingFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragmentNowPlaying).addToBackStack(null).commit();
            }
        });

        tvMoreUpcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), R.string.more_upcoming, Toast.LENGTH_SHORT).show();
                Fragment fragmentUpcoming = new UpcomingFragment();
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragmentUpcoming).addToBackStack(null).commit();
            }
        });

        getPopular();
        getNowPlaying();
        getUpcoming();

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

                    Log.i(TAG, "onResponse: title popular : " + items.getTitle());
                }
                rvPopular.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rvPopular.setLayoutManager(linearLayoutManager);
                popularAdapter = new PopularAdapter(getActivity());
                popularAdapter.setUpcomingItems(movieItems);
                rvPopular.setAdapter(popularAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }

    private void getNowPlaying() {
        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<JSONResponse> call = service.getNowPlaying();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                movieItems = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                for (int i = 0; i < movieItems.size(); i++) {
                    MovieItems items = movieItems.get(i);

                    Log.i(TAG, "onResponse: title now playing : " + items.getTitle());
                }
                rvNowPlaying.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rvNowPlaying.setLayoutManager(linearLayoutManager);
                nowPlayingAdapter = new NowPlayingAdapter(getActivity());
                nowPlayingAdapter.setNowPlayingItems(movieItems);
                rvNowPlaying.setAdapter(nowPlayingAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }

    private void getUpcoming() {
        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<JSONResponse> call = service.getUpcoming();
        call.enqueue(new Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                movieItems = new ArrayList<>(Arrays.asList(jsonResponse.getResults()));
                for (int i = 0; i < movieItems.size(); i++) {
                    MovieItems items = movieItems.get(i);

                    Log.i(TAG, "onResponse: title now playing : " + items.getTitle());
                }
                rvUpcoming.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                rvUpcoming.setLayoutManager(linearLayoutManager);
                upcomingAdapter = new UpcomingAdapter(getActivity());
                upcomingAdapter.setUpcomingItems(movieItems);
                rvUpcoming.setAdapter(upcomingAdapter);
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {

            }
        });
    }
}
