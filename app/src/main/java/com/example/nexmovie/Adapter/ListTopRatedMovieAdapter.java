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

public class ListTopRatedMovieAdapter extends RecyclerView.Adapter<ListTopRatedMovieAdapter.MyTopRated> {

    private Context context;

    private TopRatedAdapterListener listener;

    private List<MovieModel> movieTopRated;

    public class MyTopRated extends RecyclerView.ViewHolder{

        public ImageView ivPosterPathMovie;

        public MyTopRated(@NonNull View itemView) {
            super(itemView);
            ivPosterPathMovie = itemView.findViewById(R.id.ivItemTopRated);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(movieTopRated.get(getAdapterPosition()));
                }
            });
        }
    }


    public ListTopRatedMovieAdapter(Context context, ArrayList<MovieModel> movieTopRated, TopRatedAdapterListener listener) {
        this.context = context;
        this.movieTopRated = movieTopRated;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ListTopRatedMovieAdapter.MyTopRated onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_top_rated, parent, false);


        return new ListTopRatedMovieAdapter.MyTopRated(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListTopRatedMovieAdapter.MyTopRated holder, int position) {
        final MovieModel movieModel = this.movieTopRated.get(position);
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + movieModel.getPoster_path()).into(holder.ivPosterPathMovie);
    }

    @Override
    public int getItemCount() {
        return this.movieTopRated.size();
    }

    public interface TopRatedAdapterListener{


        void onContactSelected(MovieModel myTopRated);
    }
}
