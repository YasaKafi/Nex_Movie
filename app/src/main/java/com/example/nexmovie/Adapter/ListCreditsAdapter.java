package com.example.nexmovie.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nexmovie.DetailPage;
import com.example.nexmovie.Model.MovieModel;
import com.example.nexmovie.R;

import java.util.ArrayList;
import java.util.List;

public class ListCreditsAdapter extends RecyclerView.Adapter<ListCreditsAdapter.MyCredits>{

    private Context context;

    private CreditsAdapterListener listener;

    private List<MovieModel> creditMovieList;

    public ListCreditsAdapter(Context context, ArrayList<MovieModel> creditMovieList, CreditsAdapterListener listener) {
        this.context = context;
        this.creditMovieList = creditMovieList;
        this.listener =  listener;
    }

    public class MyCredits extends RecyclerView.ViewHolder {

        public ImageView ivProfilePath;

        public MyCredits(@NonNull View itemView) {
            super(itemView);
            ivProfilePath = itemView.findViewById(R.id.ivItemCredits);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onContactSelected(creditMovieList.get(getAdapterPosition()));
                }
            });
        }
    }

    @NonNull
    @Override
    public ListCreditsAdapter.MyCredits onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_credits_movie, parent, false);


        return new MyCredits(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ListCreditsAdapter.MyCredits holder, int position) {
        final MovieModel movieModel = this.creditMovieList.get(position);
        Glide.with(holder.itemView.getContext()).load("https://image.tmdb.org/t/p/w500" + movieModel.getProfile_path()).into(holder.ivProfilePath);
    }

    @Override
    public int getItemCount() {
        return this.creditMovieList.size();
    }

    public interface CreditsAdapterListener{


        void onContactSelected(MovieModel myCredits);
    }
}
