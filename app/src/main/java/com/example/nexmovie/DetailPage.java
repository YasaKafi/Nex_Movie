package com.example.nexmovie;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.example.nexmovie.Adapter.ListCreditsAdapter;
import com.example.nexmovie.Adapter.ListSimilarMovieAdapter;
import com.example.nexmovie.Model.GenreModel;
import com.example.nexmovie.Model.MovieModel;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DetailPage extends AppCompatActivity implements  ListSimilarMovieAdapter.SimilarMovieAdapterListener,ListCreditsAdapter.CreditsAdapterListener {

    Intent intent;
    RecyclerView rvMovie;
    MovieModel movieModel;
    TextView tvTitle, tvLanguage, tvOverview, tvBudget, tvReleaseDate, tvRevenue, tvRuntime, tvStatus;
    ImageView ivPosterPath, ivBackdropPath;
    ArrayList<MovieModel> listDataMovieCast;
    ArrayList<MovieModel> listSimilarMovie;
    private ListCreditsAdapter creditsAdapter;
    private ListSimilarMovieAdapter similarAdapter;
    private MovieModel myDetail;
    MovieModel similarMovie;
    ImageButton arrowBack;
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
                            myDetail.setOverview(jsonObject.getString("overview"));
                            myDetail.setBackdrop_path(jsonObject.getString("backdrop_path"));
                            myDetail.setStatus(jsonObject.getString("status"));

                            String releaseDate = jsonObject.getString("release_date");
                            String formattedReleaseDate = formatDate(releaseDate);
                            myDetail.setRelease_date(formattedReleaseDate);
                            tvReleaseDate = findViewById(R.id.tvReleaseDate);
                            tvReleaseDate.setText(formattedReleaseDate);

                            String budgetValue = jsonObject.getString("budget");
                            int budget = Integer.parseInt(budgetValue);
                            DecimalFormat decimalFormat = new DecimalFormat("#,###");
                            String formattedBudget = "$" + decimalFormat.format(budget);
                            myDetail.setBudget(formattedBudget);

                            String revenueValue = jsonObject.getString("revenue");
                            int revenue = Integer.parseInt(revenueValue);
                            DecimalFormat decimalFormatRevenue = new DecimalFormat("#,###");
                            String formattedRevenue = "$" + decimalFormatRevenue.format(revenue);
                            myDetail.setRevenue(formattedRevenue);

                            myDetail.setRuntime(jsonObject.getString("runtime"));
                            int minutes = Integer.parseInt(myDetail.getRuntime());
                            String formattedRuntime = convertMinutesToHours(minutes);

                            ivPosterPath = findViewById(R.id.ivPosterDetail);
                            ivBackdropPath = findViewById(R.id.ivBackdropPath);
                            tvRuntime = findViewById(R.id.tvRuntime);
                            tvStatus = findViewById(R.id.tvStatus);
                            tvBudget = findViewById(R.id.tvBudget);
                            tvRevenue = findViewById(R.id.tvRevenue);
                            tvLanguage = findViewById(R.id.tvLanguage);

                            tvRuntime.setText(formattedRuntime);
                            tvStatus.setText(myDetail.getStatus());
                            tvBudget.setText(myDetail.getBudget());
                            tvRevenue.setText(myDetail.getRevenue());

                            String posterPath = myDetail.getPoster_path();
                            String backdropPath = myDetail.getBackdrop_path();

                            Glide.with(DetailPage.this)
                                    .load("https://image.tmdb.org/t/p/w500" + posterPath)
                                    .into(ivPosterPath);

                            Glide.with(DetailPage.this)
                                    .load("https://image.tmdb.org/t/p/original" + backdropPath)
                                    .into(ivBackdropPath);

                            JSONArray jsonArrayGenres = jsonObject.getJSONArray("genres");
                            List<GenreModel> genreList = new ArrayList<>();
                            for (int i = 0; i < jsonArrayGenres.length(); i++) {
                                JSONObject jsonGenre = jsonArrayGenres.getJSONObject(i);
                                GenreModel genre = new GenreModel();
                                genre.setName(jsonGenre.getString("name"));
                                genreList.add(genre);
                            }
                            myDetail.setGenres(genreList);

                            ChipGroup chipGroup = findViewById(R.id.chipGroupGenres);
                            for (GenreModel genre : genreList) {
                                Chip chip = new Chip(DetailPage.this);
                                chip.setText(genre.getName());
                                chip.setChipBackgroundColorResource(R.color.white_opacity_10);
                                chip.setTextColor(getResources().getColorStateList(R.color.white));
                                chip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                                int chipHeight = getResources().getDimensionPixelSize(R.dimen.chip_height);
                                chip.setChipMinHeight(chipHeight);
                                chipGroup.addView(chip);
                            }

                            JSONArray jsonArrayLanguages = jsonObject.getJSONArray("spoken_languages");
                            List<String> languageList = new ArrayList<>();

                            for (int i = 0; i < jsonArrayLanguages.length(); i++) {
                                JSONObject jsonLanguage = jsonArrayLanguages.getJSONObject(i);
                                String languageName = jsonLanguage.getString("name");
                                languageList.add(languageName);
                            }

                            String languages = TextUtils.join(", ", languageList);
                            tvLanguage.setText(languages);


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
                                mySimilar.setId(jsonSimilar.getInt("id"));
                                mySimilar.setTitle(jsonSimilar.getString("title"));
                                mySimilar.setBackdrop_path(jsonSimilar.getString("backdrop_path"));
                                mySimilar.setOverview(jsonSimilar.getString("overview"));
                                mySimilar.setRelease_date(jsonSimilar.getString("release_date"));

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        listSimilarMovie = new ArrayList<>();
        listDataMovieCast = new ArrayList<>();

        intent = getIntent();
        movieModel = intent.getParcelableExtra("myMovie");
        similarMovie = intent.getParcelableExtra("mySimilar");

        movieID = movieModel.getId();
        arrowBack = findViewById(R.id.arrowBack);

        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        tvOverview.setText(movieModel.getOverview());
        tvTitle.setText(movieModel.getTitle());


        getCredits();
        getSimilar();
        getDetail();

        arrowBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backActivity = new Intent(DetailPage.this,NavigationBar.class);
                startActivity(backActivity);
            }
        });

    }

    private String formatDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());

        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString;
        }
    }




    private String convertMinutesToHours(int minutes) {
        int hours = minutes / 60;
        int remainingMinutes = minutes % 60;
        return String.format(Locale.getDefault(), "%dh %02dmin", hours, remainingMinutes);
    }



    @Override
    public void onContactSelected(MovieModel mySimilar) {
        Intent intent = new Intent(this, DetailPage.class);
        intent.putExtra("myMovie", mySimilar);
        startActivity(intent);


    }
}