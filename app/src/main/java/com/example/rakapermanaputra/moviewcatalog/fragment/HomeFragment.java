package com.example.rakapermanaputra.moviewcatalog.fragment;


import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.adapter.MoreNowPlayingAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.NowPlayingAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.PopularAdapter;
import com.example.rakapermanaputra.moviewcatalog.adapter.UpcomingAdapter;
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
    private List<Result> movieList;
    private PopularAdapter popularAdapter;
    private NowPlayingAdapter nowPlayingAdapter;
    private UpcomingAdapter upcomingAdapter;

    List<Result> listUpcoming;
    List<Result> listNowPlaying;

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
                getFragmentManager().beginTransaction().replace(R.id.content_main, fragmentPopular).addToBackStack(null).commit();
            }
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

//        getPopular();
//        getNowPlaying();
//        getUpcoming();

        initViewNowPlaying();
        initViewPopular();
        initViewUpcoming();

        if (savedInstanceState != null) {

            movieList = savedInstanceState.getParcelableArrayList("popular");
            listNowPlaying = savedInstanceState.getParcelableArrayList("now_playing");
            listUpcoming = savedInstanceState.getParcelableArrayList("upcoming");

            popularAdapter = new PopularAdapter(getActivity());
            popularAdapter.setPopularItems(HomeFragment.this.movieList);
            rvPopular.setAdapter(popularAdapter);

            nowPlayingAdapter = new NowPlayingAdapter(getActivity());
            nowPlayingAdapter.setNowPlayingItems(HomeFragment.this.movieList);
            rvNowPlaying.setAdapter(nowPlayingAdapter);

            upcomingAdapter = new UpcomingAdapter(getActivity());
            upcomingAdapter.setUpcomingItems(HomeFragment.this.movieList);
            rvUpcoming.setAdapter(upcomingAdapter);
        } else {
            getUpcoming();
            getNowPlaying();
            getPopular();
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (movieList == null) {
            getPopular();
//            getNowPlaying();
//            getUpcoming();
        }else{
//            outState.putParcelableArrayList("now_playing", new ArrayList<>(listNowPlaying));
//            outState.putParcelableArrayList("up_coming", new ArrayList<>(listUpcoming));
            outState.putParcelableArrayList("popular", new ArrayList<>(movieList));
        }

        if (listNowPlaying == null) {
            getNowPlaying();
        } else {
            outState.putParcelableArrayList("now_playing", new ArrayList<>(listNowPlaying));
        }

        if (listUpcoming == null) {
            getUpcoming();
        } else {
            outState.putParcelableArrayList("upcoming", new ArrayList<>(listUpcoming));
        }
    }

    private void initViewUpcoming() {
        rvUpcoming.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvUpcoming.setLayoutManager(linearLayoutManager);
    }

    private void initViewPopular() {
        rvPopular.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvPopular.setLayoutManager(linearLayoutManager);
    }

    private void initViewNowPlaying() {
        rvNowPlaying.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        rvNowPlaying.setLayoutManager(linearLayoutManager);
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

                    Log.i(TAG, "POPULAR " + "get title : " + result.getTitle());
                }
//                rvPopular.setHasFixedSize(true);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                rvPopular.setLayoutManager(linearLayoutManager);
                popularAdapter = new PopularAdapter(getActivity());
                popularAdapter.setPopularItems(HomeFragment.this.movieList);
                rvPopular.setAdapter(popularAdapter);
            }

            @Override
            public void onFailure(Call<MovieItems> call, Throwable t) {

            }
        });
    }

    private void getNowPlaying() {
        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<MovieItems> call = service.getNowPlaying();
        call.enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
                MovieItems movieItems = response.body();
                listNowPlaying = movieItems.getResults();

                for (int i = 0; i < listNowPlaying.size(); i++) {
                    Result result = listNowPlaying.get(i);

                    Log.i(TAG, "NOW PLAYING " + "title : " + result.getTitle());
                }
//                rvNowPlaying.setHasFixedSize(true);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                rvNowPlaying.setLayoutManager(linearLayoutManager);
                nowPlayingAdapter = new NowPlayingAdapter(getActivity());
                nowPlayingAdapter.setNowPlayingItems(HomeFragment.this.listNowPlaying);
                rvNowPlaying.setAdapter(nowPlayingAdapter);
            }

            @Override
            public void onFailure(Call<MovieItems> call, Throwable t) {

            }
        });
    }

    private void getUpcoming() {
        ApiService service = RetrofitClientInstance.retrofit().create(ApiService.class);
        Call<MovieItems> call = service.getUpcoming();
        call.enqueue(new Callback<MovieItems>() {
            @Override
            public void onResponse(Call<MovieItems> call, Response<MovieItems> response) {
                MovieItems movieItems = response.body();
                listUpcoming = movieItems.getResults();

                for (int i = 0; i < listUpcoming.size(); i++) {
                    Result result = listUpcoming.get(i);

                    Log.i(TAG, "UPCOMING " + "title : " + result.getTitle());
                }
//                rvUpcoming.setHasFixedSize(true);
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
//                rvUpcoming.setLayoutManager(linearLayoutManager);
                upcomingAdapter = new UpcomingAdapter(getActivity());
                upcomingAdapter.setUpcomingItems(HomeFragment.this.listUpcoming);
                rvUpcoming.setAdapter(upcomingAdapter);
            }

            @Override
            public void onFailure(Call<MovieItems> call, Throwable t) {

            }
        });
    }
}
