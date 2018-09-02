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
import com.example.rakapermanaputra.moviewcatalog.model.Result;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder> {
    private List<Result> movieItems;
    private Context context;

    public RecommendationAdapter(Context context) {
        this.context = context;
    }

    public void setMovieItems(List<Result> movieItems) {
        this.movieItems = movieItems;
    }

    public List<Result> getMovieItems() {
        return movieItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Result items = movieItems.get(position);

        holder.recommendItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, items.getTitle(), Toast.LENGTH_SHORT).show();
                // ambil data
                items.getTitle();
                items.getId();
                items.getReleaseDate();
                items.getOverview();
                items.getVoteAverage();
                items.getPosterPath();
                // intent ke DetailActivity
                Intent intent = new Intent(context, DetailActivity.class);
                // menggunakan parcelable
                intent.putExtra(DetailActivity.EXTRA_DATA, items);
                context.startActivity(intent);
            }
        });

        holder.tvTitle.setText(items.getTitle());

        Glide.with(context)
                .load("http://image.tmdb.org/t/p/w185" + items.getPosterPath())
                .override(130, 180)
                .into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return getMovieItems().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.img_poster) ImageView imgPoster;
        @BindView(R.id.tv_title) TextView tvTitle;
        @BindView(R.id.recommendation_item) LinearLayout recommendItem;


        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
