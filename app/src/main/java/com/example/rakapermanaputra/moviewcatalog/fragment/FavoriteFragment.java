package com.example.rakapermanaputra.moviewcatalog.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.adapter.FavoriteAdapter;
import com.example.rakapermanaputra.moviewcatalog.model.Result;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment {

    private ArrayList<Result> movieItems;
    private FavoriteAdapter adapter;
    @BindView(R.id.recyclerViewFavorite)
    RecyclerView recyclerViewFavorite;
    @BindView(R.id.imgFav)
    ImageView imgFav;

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);

        return view;
    }

}
