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

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.MyMovie> {

    private Context context;

    private MovieAdapterListener listener;

    private List<MovieModel> movieModelList;

    public ListMovieAdapter(Context context, ArrayList<MovieModel> movieModelList, MovieAdapterListener listener) {
        this.context = context;
        this.movieModelList = movieModelList;
        this.listener = listener;
    }


    public class MyMovie extends RecyclerView.ViewHolder{

        public ImageView ivPosterPathMovie;

        public MyMovie(@NonNull View itemView) {
            super(itemView);
            ivPosterPathMovie = itemView.findViewById(R.id.ivItemMovie);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(movieModelList.get(getAdapterPosition()));
                }
            });
        }
    }


    @NonNull
    @Override
    public ListMovieAdapter.MyMovie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_movie_layout, parent, false);


        return new MyMovie(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListMovieAdapter.MyMovie holder, int position) {
        final MovieModel movieModel = this.movieModelList.get(position);
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + movieModel.getPoster_path()).into(holder.ivPosterPathMovie);
    }

    @Override
    public int getItemCount() {
        return this.movieModelList.size();
    }

    public interface MovieAdapterListener{


        void onContactSelected(MovieModel myMovie);
    }
}

