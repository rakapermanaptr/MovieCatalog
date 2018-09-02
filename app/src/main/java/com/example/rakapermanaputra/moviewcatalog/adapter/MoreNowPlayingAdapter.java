package com.example.rakapermanaputra.moviewcatalog.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
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
import com.example.rakapermanaputra.moviewcatalog.model.MovieItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoreNowPlayingAdapter extends RecyclerView.Adapter<MoreNowPlayingAdapter.ViewHolder> {
    private ArrayList<MovieItems> movieItems;
    private Context context;

    public MoreNowPlayingAdapter(Context context) {
        this.context = context;
    }

    public void setMovieItems(ArrayList<MovieItems> movieItems) {
        this.movieItems = movieItems;
    }

    public ArrayList<MovieItems> getMovieItems() {
        return movieItems;
    }

    @NonNull
    @Override
    public MoreNowPlayingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MovieItems items = movieItems.get(position);

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185" + items.getPoster_path())
                .override(130, 180)
                .into(holder.imgPoster);

        holder.tvTitle.setText(items.getTitle());
        holder.tvOverview.setText(items.getOverview());
        holder.tvReleaseDate.setText(items.getRelease_date());

        holder.listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, DetailActivity.class);
                intent.hasExtra("title");
                intent.putExtra("title", items.getTitle());
                intent.putExtra("id", items.getId());
                intent.putExtra("release_date", items.getRelease_date());
                intent.putExtra("overview", items.getOverview());
                intent.putExtra("vote_average", items.getVote_average());
                intent.putExtra("backdrop_path", items.getBackdrop_path());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return getMovieItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_poster) ImageView imgPoster;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.tv_overview) TextView tvOverview;
        @BindView(R.id.tv_release_date) TextView tvReleaseDate;
        @BindView(R.id.list_item) LinearLayout listItem;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
