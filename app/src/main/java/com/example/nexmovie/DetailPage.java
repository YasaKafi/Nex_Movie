package com.example.nexmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.nexmovie.Adapter.ListCreditsAdapter;
import com.example.nexmovie.Adapter.ListDetailAdapter;
import com.example.nexmovie.Adapter.ListSimilarMovieAdapter;
import com.example.nexmovie.Model.GenreModel;
import com.example.nexmovie.Model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DetailPage extends AppCompatActivity implements ListSimilarMovieAdapter.SimilarMovieAdapterListener, ListCreditsAdapter.CreditsAdapterListener, ListDetailAdapter.DetailAdapterListener {

    Intent intent;
    RecyclerView rvMovie;
    MovieModel movieModel;
    TextView tvTitle, tvGenres, tvFormedYear, tvOverview, tvBudget, tvReleaseDate, tvRevenue, tvRuntime, tvStatus, tvName;
    ImageView ivPosterPath, ivBackdropPath, ivProfilePath;
    ArrayList<MovieModel> listDataMovieCast;
    ArrayList<MovieModel> listSimilarMovie;
    ArrayList<MovieModel> listDetailMovie;
    private ListCreditsAdapter creditsAdapter;
    private ListSimilarMovieAdapter similarAdapter;
    private ListDetailAdapter detailAdapter;

    private MovieModel myDetail;

    int movieID;

    private void getCredits() {
        String url = "https://api.themoviedb.org/3/movie/" + movieID + "/credits?api_key=3d304a8d6d6a05df31e454b80c9722ba&language=en-US";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonCreditArray = jsonObject.getJSONArray("cast");
                            for (int i = 0; i < jsonCreditArray.length(); i++) {
                                MovieModel myCredit = new MovieModel();
                                JSONObject jsonCredit = jsonCreditArray.getJSONObject(i);
                                myCredit.setProfile_path(jsonCredit.getString("profile_path"));
                                myCredit.setName(jsonCredit.getString("name"));

                                listDataMovieCast.add(myCredit);
                            }
                            //ganti id di xml detail page
                            rvMovie = findViewById(R.id.rvCredits);
                            creditsAdapter = new ListCreditsAdapter(DetailPage.this, listDataMovieCast, DetailPage.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DetailPage.this, RecyclerView.HORIZONTAL, false);
                            rvMovie.setHasFixedSize(true);
                            rvMovie.setLayoutManager(mLayoutManager);
                            rvMovie.setAdapter(creditsAdapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Log.d("error", "onError: " + anError.toString());
                    }
                });

    }
    private void getSimilar() {
        String url = "https://api.themoviedb.org/3/movie/" + movieID + "/similar?api_key=3d304a8d6d6a05df31e454b80c9722ba&language=en-US";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            JSONArray jsonArraySimilar = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArraySimilar.length(); i++) {
                                MovieModel mySimilar = new MovieModel();
                                JSONObject jsonSimilar = jsonArraySimilar.getJSONObject(i);
                                mySimilar.setPoster_path(jsonSimilar.getString("poster_path"));
                                listSimilarMovie.add(mySimilar);
                            }
                            //ganti id di xml detail page
                            rvMovie = findViewById(R.id.rvSimilar);
                            similarAdapter = new ListSimilarMovieAdapter(DetailPage.this, listSimilarMovie, DetailPage.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(DetailPage.this, RecyclerView.HORIZONTAL, false);
                            rvMovie.setHasFixedSize(true);
                            rvMovie.setLayoutManager(mLayoutManager);
                            rvMovie.setAdapter(similarAdapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Log.d("error", "onError: " + anError.toString());
                    }
                });

    }

    private void getDetail() {
        String url = "https://api.themoviedb.org/3/movie/" + movieID + "?api_key=3d304a8d6d6a05df31e454b80c9722ba&language=en-US";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        try {
                            myDetail = new MovieModel();
                            myDetail.setPoster_path(jsonObject.getString("poster_path"));
                            myDetail.setTitle(jsonObject.getString("title"));
                            myDetail.setId(jsonObject.getInt("id"));
                            myDetail.setRelease_date(jsonObject.getString("release_date"));
                            myDetail.setOverview(jsonObject.getString("overview"));
                            myDetail.setBudget(jsonObject.getString("budget"));
                            myDetail.setRuntime(jsonObject.getString("runtime"));
                            myDetail.setBackdrop_path(jsonObject.getString("backdrop_path"));
                            myDetail.setStatus(jsonObject.getString("status"));
                            myDetail.setRevenue(jsonObject.getString("revenue"));

                            JSONArray jsonArrayGenres = jsonObject.getJSONArray("genres");
                            List<GenreModel> genreList = new ArrayList<>();
                            for (int i = 0; i < jsonArrayGenres.length(); i++) {
                                JSONObject jsonGenre = jsonArrayGenres.getJSONObject(i);
                                GenreModel genre = new GenreModel();

                                genre.setName(jsonGenre.getString("name"));
                                genreList.add(genre);
                            }
                            myDetail.setGenres(genreList);


                            tvReleaseDate = findViewById(R.id.tvRealise);
                            StringBuilder genresStringBuilder = new StringBuilder();
                            for (GenreModel genre : genreList) {
                                genresStringBuilder.append(genre.getName()).append(", ");
                            }
                            String genresString = genresStringBuilder.toString().trim();
                            tvReleaseDate.setText(genresString);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        // handle error
                        Log.d("error", "onError: " + anError.toString());
                    }
                });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);

        listSimilarMovie = new ArrayList<>();
        listDataMovieCast = new ArrayList<>();

        intent = getIntent();
        movieModel = intent.getParcelableExtra("myMovie");
        movieID = movieModel.getId();



        getCredits();
        getSimilar();
        getDetail();
    }





    @Override
    public void onContactSelected(MovieModel mySimilar) {

    }
}