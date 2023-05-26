package com.example.nexmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.nexmovie.Adapter.ListMovieAdapter;
import com.example.nexmovie.Adapter.ListSimilarMovieAdapter;
import com.example.nexmovie.Adapter.ListTopRatedMovieAdapter;
import com.example.nexmovie.Model.MovieModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ListMovieAdapter.MovieAdapterListener, ListTopRatedMovieAdapter.TopRatedAdapterListener {

    RecyclerView rvMovie;
    ArrayList<MovieModel> listDataPopularMovie;
    ArrayList<MovieModel> listDataTopRatedMovie;
    ArrayList<MovieModel> listDataNowPlayingMovie;
    private ListMovieAdapter listMovieAdapter;

    private ListTopRatedMovieAdapter listTopRatedMovieAdapter;


    public void getPopularMovie(){
        ProgressBar progressBar = findViewById(R.id.tvproges);
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=3d304a8d6d6a05df31e454b80c9722ba&language=en-US&page=1";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                        try {
                            JSONArray jsonArrayPopular = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArrayPopular.length(); i++) {
                                MovieModel myPopular = new MovieModel();
                                JSONObject jsonPopular = jsonArrayPopular.getJSONObject(i);
                                myPopular.setTitle(jsonPopular.getString("title"));
                                myPopular.setId(jsonPopular.getInt("id"));
                                myPopular.setPoster_path(jsonPopular.getString("poster_path"));
                                myPopular.setRelease_date(jsonPopular.getString("release_date"));
                                myPopular.setOverview(jsonPopular.getString("overview"));
                                listDataPopularMovie.add(myPopular);


                            }
                            rvMovie =  (RecyclerView) findViewById(R.id.rvPopular);
                            listMovieAdapter = new ListMovieAdapter(getApplicationContext(), listDataPopularMovie,MainActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
                            rvMovie.setHasFixedSize(true);
                            rvMovie.setLayoutManager(mLayoutManager);
                            rvMovie.setAdapter(listMovieAdapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "onError: " + anError.toString());
                    }
                });
    }
    public void getTopRatedMovie(){
        ProgressBar progressBar = findViewById(R.id.tvproges);
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=3d304a8d6d6a05df31e454b80c9722ba&language=en-US&page=1";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                        try {
                            JSONArray jsonArrayTopRated = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArrayTopRated.length(); i++) {
                                MovieModel myTopRated = new MovieModel();
                                JSONObject jsonMovie = jsonArrayTopRated.getJSONObject(i);
                                myTopRated.setPoster_path(jsonMovie.getString("poster_path"));
                                myTopRated.setTitle(jsonMovie.getString("title"));
                                myTopRated.setId(jsonMovie.getInt("id"));
                                myTopRated.setRelease_date(jsonMovie.getString("release_date"));
                                myTopRated.setOverview(jsonMovie.getString("overview"));
                                listDataTopRatedMovie.add(myTopRated);
                            }
                            rvMovie =  (RecyclerView) findViewById(R.id.rvTopRated);
                            listTopRatedMovieAdapter = new ListTopRatedMovieAdapter(getApplicationContext(), listDataTopRatedMovie, MainActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
                            rvMovie.setHasFixedSize(true);
                            rvMovie.setLayoutManager(mLayoutManager);
                            rvMovie.setAdapter(listTopRatedMovieAdapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "onError: " + anError.toString());
                    }
                });
    }

    public void getNowPlayingMovie(){
        ProgressBar progressBar = findViewById(R.id.tvproges);
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=3d304a8d6d6a05df31e454b80c9722ba&language=en-US&page=1";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                        try {
                            JSONArray jsonArrayNowPlaying = jsonObject.getJSONArray("results");
                            for (int i = 0; i < jsonArrayNowPlaying.length(); i++) {
                                MovieModel myNowPlaying = new MovieModel();
                                JSONObject jsonNowPlaying = jsonArrayNowPlaying.getJSONObject(i);
                                myNowPlaying.setPoster_path(jsonNowPlaying.getString("poster_path"));
                                myNowPlaying.setTitle(jsonNowPlaying.getString("title"));
                                myNowPlaying.setId(jsonNowPlaying.getInt("id"));
                                myNowPlaying.setRelease_date(jsonNowPlaying.getString("release_date"));
                                myNowPlaying.setOverview(jsonNowPlaying.getString("overview"));


                                listDataNowPlayingMovie.add(myNowPlaying);
                            }
                            rvMovie =  (RecyclerView) findViewById(R.id.rvNowPlaying);
                            listMovieAdapter = new ListMovieAdapter(getApplicationContext(), listDataNowPlayingMovie, MainActivity.this);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this, RecyclerView.HORIZONTAL, false);
                            rvMovie.setHasFixedSize(true);
                            rvMovie.setLayoutManager(mLayoutManager);
                            rvMovie.setAdapter(listMovieAdapter);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", "onError: " + anError.toString());
                    }
                });
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listDataPopularMovie = new ArrayList<>();
        listDataTopRatedMovie = new ArrayList<>();
        listDataNowPlayingMovie = new ArrayList<>();
        setContentView(R.layout.activity_main);

        getPopularMovie();
        getTopRatedMovie();
        getNowPlayingMovie();
    }

    @Override
    public void onContactSelected(MovieModel myMovie) {
        Intent intent = new Intent(MainActivity.this, DetailPage.class);
        intent.putExtra("myMovie", myMovie);
        startActivity(intent);
    }
}