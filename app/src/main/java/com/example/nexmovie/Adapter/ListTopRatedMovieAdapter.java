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
                                movieTopRated.remove(holder.getAdapterPosition());
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
        return this.movieTopRated.size();
    }

    public interface TopRatedAdapterListener{


        void onContactSelected(MovieModel myTopRated);
    }
}
