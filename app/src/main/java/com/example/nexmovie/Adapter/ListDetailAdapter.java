package com.example.nexmovie.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nexmovie.Model.MovieModel;

import java.util.ArrayList;
import java.util.List;

public class ListDetailAdapter extends RecyclerView.Adapter<ListDetailAdapter.myDetailHolder> {

    private Context context;

    private DetailAdapterListener listener;

    private List<MovieModel> detailMovieList;

    public ListDetailAdapter(Context context, ArrayList<MovieModel> detailMovieList, DetailAdapterListener listener) {
        this.context = context;
        this.detailMovieList = detailMovieList;
        this.listener =  listener;
    }

    public class myDetailHolder extends RecyclerView.ViewHolder {
        public myDetailHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    @NonNull
    @Override
    public ListDetailAdapter.myDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ListDetailAdapter.myDetailHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public interface DetailAdapterListener{


        void onContactSelected(MovieModel myDetail);
    }
}
