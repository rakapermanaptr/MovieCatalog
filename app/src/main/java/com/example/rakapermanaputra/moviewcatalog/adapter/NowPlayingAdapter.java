package com.example.rakapermanaputra.moviewcatalog.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.activity.DetailActivity;
import com.example.rakapermanaputra.moviewcatalog.model.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bumptech.glide.gifdecoder.GifHeaderParser.TAG;

public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {
    private List<Result> nowPlayingItems;
    private Context context;

    public NowPlayingAdapter(Context context) {
        this.context = context;
    }

    public void setNowPlayingItems(List<Result> nowPlayingItems) {
        this.nowPlayingItems = nowPlayingItems;
    }

    public List<Result> getNowPlayingItems() {
        return nowPlayingItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_now_playing, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Result items = nowPlayingItems.get(position);

        holder.listNowPlayingitem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, items.getTitle(), Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(context, DetailActivity.class);
                intent.hasExtra("title");
                intent.putExtra("title", items.getTitle());
                intent.putExtra("id", items.getId());
                intent.putExtra("release_date", items.getReleaseDate());
                intent.putExtra("overview", items.getOverview());
                intent.putExtra("vote_average", items.getVoteAverage());
                intent.putExtra("backdrop_path", items.getBackdropPath());

                Log.i(TAG, "onClick: get vote : " + items.getVoteAverage());

                context.startActivity(intent);
            }
        });

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185" + items.getPosterPath())
                .override(130, 180)
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return nowPlayingItems.size()-10;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.now_playing_item) LinearLayout listNowPlayingitem;
        @BindView(R.id.img_poster) ImageView imgPoster;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
