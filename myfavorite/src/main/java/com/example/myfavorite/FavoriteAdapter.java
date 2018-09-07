package com.example.myfavorite;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.myfavorite.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.myfavorite.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.example.myfavorite.DatabaseContract.MovieColumns.RELEASE_DATE;
import static com.example.myfavorite.DatabaseContract.MovieColumns.TITLE;
import static com.example.myfavorite.DatabaseContract.getColumnString;


public class FavoriteAdapter extends CursorAdapter {

    @BindView(R.id.tv_title) TextView tvTitle;
    @BindView(R.id.tv_overview) TextView tvOverview;
    @BindView(R.id.tv_release_date) TextView tvRelease_date;
    @BindView(R.id.img_poster) ImageView imgPoster;
    @BindView(R.id.btn_fav) MaterialFavoriteButton btnFav;

    public FavoriteAdapter(Context context, Cursor c, boolean autoRequery) {
        super(context, c, autoRequery);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_favorite, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        if (cursor != null) {

            ButterKnife.bind(this, view);

            String poster_url = getColumnString(cursor, POSTER_PATH);

            tvTitle.setText(getColumnString(cursor, TITLE));
            tvOverview.setText(getColumnString(cursor, OVERVIEW));
            tvRelease_date.setText(getColumnString(cursor, RELEASE_DATE));

            Glide.with(context)
                    .load("http://image.tmdb.org/t/p/w185" + poster_url)
                    .override(130, 180)
                    .into(imgPoster);

            btnFav.setFavorite(true);

        }

    }
}
