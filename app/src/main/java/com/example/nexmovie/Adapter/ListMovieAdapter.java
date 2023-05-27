package com.example.nexmovie.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupWindow;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.nexmovie.DetailPage;
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
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View customDialogView = inflater.inflate(R.layout.custom_action_sheet, null);

                final PopupWindow popupWindow = new PopupWindow(customDialogView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
                popupWindow.setAnimationStyle(R.anim.slide_up);
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);

                ImageButton detailButton = customDialogView.findViewById(R.id.ibDetailButton);
                ImageButton deleteButton = customDialogView.findViewById(R.id.ibDeleteButton);
                Button cancelButton = customDialogView.findViewById(R.id.ibCancelButton);

                cancelButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        popupWindow.dismiss();
                    }
                });

                detailButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(view.getContext(), DetailPage.class);
                        intent.putExtra("myMovie", movieModel);
                        view.getContext().startActivity(intent);
                        popupWindow.dismiss();
                    }
                });

                // Menambahkan aksi ketika tombol "Tidak" ditekan
                deleteButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

                        LayoutInflater inflater = LayoutInflater.from(view.getContext());
                        View dialogView = inflater.inflate(R.layout.custom_alert_dialog, null);

                        ImageView imageView = dialogView.findViewById(R.id.imageView);
                        Button yesButton = dialogView.findViewById(R.id.yesButton);
                        Button noButton = dialogView.findViewById(R.id.noButton);

                        // Set gambar dari API ke ImageView
                        Glide.with(view.getContext())
                                .load("https://image.tmdb.org/t/p/w500" + movieModel.getPoster_path())
                                .into(imageView);

                        builder.setView(dialogView);
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                        // Menambahkan aksi ketika tombol "Ya" ditekan
                        yesButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Hapus item dari list
                                movieModelList.remove(holder.getAdapterPosition());
                                // Memberitahu adapter bahwa item telah dihapus
                                notifyItemRemoved(holder.getAdapterPosition());
                                // Tutup dialog
                                alertDialog.dismiss();
                            }
                        });

                        // Menambahkan aksi ketika tombol "Tidak" ditekan
                        noButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // Tutup dialog
                                alertDialog.dismiss();
                            }
                        });
                    }
                });
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return this.movieModelList.size();
    }

    public interface MovieAdapterListener{


        void onContactSelected(MovieModel myMovie);
    }
}

