package com.example.nexmovie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nexmovie.Model.MovieModel;
import com.example.nexmovie.R;

import java.util.ArrayList;
import java.util.List;



public class ListSimilarMovieAdapter extends RecyclerView.Adapter<ListSimilarMovieAdapter.MySimilar>{

    private Context context;

    private SimilarMovieAdapterListener listener;

    private List<MovieModel> movieSimilarList;

    public ListSimilarMovieAdapter(Context context, ArrayList<MovieModel> movieSimilarList, SimilarMovieAdapterListener listener) {
        this.context = context;
        this.movieSimilarList = movieSimilarList;
        this.listener =  listener;
    }

    public class MySimilar extends RecyclerView.ViewHolder {

        public ImageView ivPosterPathSimilar;

        public MySimilar(@NonNull View itemView) {
            super(itemView);
            ivPosterPathSimilar = itemView.findViewById(R.id.ivItemSimilar);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(movieSimilarList.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public ListSimilarMovieAdapter.MySimilar onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_similar_movie, parent, false);


        return new MySimilar(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListSimilarMovieAdapter.MySimilar holder, int position) {
        final MovieModel movieModel = this.movieSimilarList.get(position);
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + movieModel.getPoster_path()).into(holder.ivPosterPathSimilar);
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.movieSimilarList.size();
    }

    public interface SimilarMovieAdapterListener{


        void onContactSelected(MovieModel mySimilar);

    }
}


