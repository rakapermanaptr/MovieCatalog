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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.activity.DetailActivity;
import com.example.rakapermanaputra.moviewcatalog.model.MovieItems;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.ViewHolder> {
    private ArrayList<MovieItems> upcomingItems;
    private Context context;

    public UpcomingAdapter(Context context) {
        this.context = context;
    }

    public ArrayList<MovieItems> getUpcomingItems() {
        return upcomingItems;
    }

    public void setUpcomingItems(ArrayList<MovieItems> upcomingItems) {
        this.upcomingItems = upcomingItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_upcoming, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final MovieItems items = upcomingItems.get(position);

        holder.listUpcomingItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, items.getTitle(), Toast.LENGTH_SHORT).show();

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

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185" + items.getPoster_path())
                .override(130, 180)
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return getUpcomingItems().size()-10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.upcoming_item) LinearLayout listUpcomingItem;
        @BindView(R.id.img_poster) ImageView imgPoster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
