package com.example.rakapermanaputra.moviewcatalog.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.activity.DetailActivity;
import com.example.rakapermanaputra.moviewcatalog.activity.MainActivity;
import com.example.rakapermanaputra.moviewcatalog.database.MovieHelper;
import com.example.rakapermanaputra.moviewcatalog.model.Result;
import com.github.ivbaranov.mfb.MaterialFavoriteButton;
import java.util.LinkedList;
import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.rakapermanaputra.moviewcatalog.database.DatabaseContract.CONTENT_URI;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Cursor movieItems;
    private Activity activity;

    public FavoriteAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setMovieItems(Cursor movieItems) {
        this.movieItems = movieItems;
    }

    public Cursor getMovieItems() {
        return movieItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_favorite, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        final Result items = getItem(position);

        Glide.with(activity)
                .load("http://image.tmdb.org/t/p/w185" + items.getPosterPath())
                .override(130, 180)
                .into(holder.imgPoster);

        holder.tvTitle.setText(items.getTitle());
        holder.tvOverview.setText(items.getOverview());
        holder.tvReleaseDate.setText(items.getReleaseDate());
        holder.favoriteButton.setFavorite(true);

        holder.favoriteButton.setOnFavoriteChangeListener(new MaterialFavoriteButton.OnFavoriteChangeListener() {
            @Override
            public void onFavoriteChanged(MaterialFavoriteButton buttonView, boolean favorite) {
                Toast.makeText(activity,  items.getTitle() + " Deleted from Favorite", Toast.LENGTH_SHORT).show();

                activity.getContentResolver().delete(
                        Uri.parse(CONTENT_URI + "/" + items.getId()),
                        null,
                        null);
                activity.startActivity(new Intent(activity, MainActivity.class));
            }
        });

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(activity, "Test " + items.getTitle(), Toast.LENGTH_SHORT).show();


            }
        });

    }

    @Override
    public int getItemCount() {
        if (movieItems == null) return 0;
        return movieItems.getCount();
    }

    private Result getItem(int position) {
        if (!movieItems.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Result(movieItems);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_poster)
        ImageView imgPoster;
        @BindView(R.id.tv_title)
        TextView tvTitle;
        @BindView(R.id.tv_overview)
        TextView tvOverview;
        @BindView(R.id.tv_release_date)
        TextView tvReleaseDate;
        @BindView(R.id.cardView)
        CardView cardView;
        @BindView(R.id.btn_fav)
        MaterialFavoriteButton favoriteButton;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
