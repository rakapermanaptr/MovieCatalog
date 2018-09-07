package com.example.rakapermanaputra.moviewcatalog.fragment;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.adapter.FavoriteAdapter;
import com.example.rakapermanaputra.moviewcatalog.database.MovieHelper;
import com.example.rakapermanaputra.moviewcatalog.model.Result;

import java.util.ArrayList;
import java.util.LinkedList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.rakapermanaputra.moviewcatalog.database.DatabaseContract.CONTENT_URI;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private Cursor movieList;
    private FavoriteAdapter adapter;
    private MovieHelper movieHelper;

    @BindView(R.id.recyclerViewFavorite)
    RecyclerView recyclerViewFavorite;


    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        ButterKnife.bind(this, view);

        recyclerViewFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerViewFavorite.setHasFixedSize(true);

        adapter = new FavoriteAdapter(getActivity());
        adapter.setMovieItems(movieList);
        recyclerViewFavorite.setAdapter(adapter);

        new LoadMovieAsync().execute();

        return view;

    }

    private class LoadMovieAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor results) {
            super.onPostExecute(results);

            movieList = results;
            adapter.setMovieItems(movieList);
            adapter.notifyDataSetChanged();

            if (movieList.getCount() == 0) {
                Toast.makeText(getContext(), "Tidak ada data saat ini", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
