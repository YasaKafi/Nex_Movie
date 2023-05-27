package com.example.nexmovie.Fragment;

import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.nexmovie.Adapter.ListMovieAdapter;
import com.example.nexmovie.Adapter.ListTopRatedMovieAdapter;
import com.example.nexmovie.DetailPage;
import com.example.nexmovie.Model.MovieModel;
import com.example.nexmovie.R;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class HomeFragment extends Fragment implements ListMovieAdapter.MovieAdapterListener, ListTopRatedMovieAdapter.TopRatedAdapterListener {

    private RecyclerView rvPopular;
    private RecyclerView rvTopRated;
    private RecyclerView rvNowPlaying;
    private ProgressBar progressBar;
    private ArrayList<MovieModel> listDataPopularMovie;
    private ArrayList<MovieModel> listDataTopRatedMovie;
    private ArrayList<MovieModel> listDataNowPlayingMovie;
    private ListMovieAdapter listMovieAdapter;
    private ListTopRatedMovieAdapter listTopRatedMovieAdapter;

    public HomeFragment() {
        // Diperlukan konstruktor kosong untuk Fragment
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rvPopular = view.findViewById(R.id.rvPopular2);
        rvTopRated = view.findViewById(R.id.rvTopRated2);
        rvNowPlaying = view.findViewById(R.id.rvNowPlaying2);
        progressBar = view.findViewById(R.id.tvProges);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listDataPopularMovie = new ArrayList<>();
        listDataTopRatedMovie = new ArrayList<>();
        listDataNowPlayingMovie = new ArrayList<>();

        getPopularMovie();
        getTopRatedMovie();
        getNowPlayingMovie();
    }

    public void getPopularMovie() {
        String url = "https://api.themoviedb.org/3/movie/popular?api_key=3d304a8d6d6a05df31e454b80c9722ba&language=en-US&page=1";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressBar.setVisibility(View.GONE);
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
                            listMovieAdapter = new ListMovieAdapter(requireContext(), listDataPopularMovie, HomeFragment.this);
                            rvPopular.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
                            rvPopular.setHasFixedSize(true);
                            rvPopular.setAdapter(listMovieAdapter);
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

    public void getTopRatedMovie() {
        String url = "https://api.themoviedb.org/3/movie/top_rated?api_key=3d304a8d6d6a05df31e454b80c9722ba&language=en-US&page=1";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressBar.setVisibility(View.GONE);
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
                            listTopRatedMovieAdapter = new ListTopRatedMovieAdapter(requireContext(), listDataTopRatedMovie, HomeFragment.this);
                            rvTopRated.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
                            rvTopRated.setHasFixedSize(true);
                            rvTopRated.setAdapter(listTopRatedMovieAdapter);
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

    public void getNowPlayingMovie() {
        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=3d304a8d6d6a05df31e454b80c9722ba&language=en-US&page=1";
        AndroidNetworking.get(url)
                .setTag("test")
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        progressBar.setVisibility(View.GONE);
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
                            listMovieAdapter = new ListMovieAdapter(requireContext(), listDataNowPlayingMovie, HomeFragment.this);
                            rvNowPlaying.setLayoutManager(new LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false));
                            rvNowPlaying.setHasFixedSize(true);
                            rvNowPlaying.setAdapter(listMovieAdapter);
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
    public void onContactSelected(MovieModel myMovie) {
        Intent intent = new Intent(requireContext(), DetailPage.class);
        intent.putExtra("myMovie", myMovie);
        startActivity(intent);
    }
}
