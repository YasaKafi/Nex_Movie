package com.example.nexmovie.Model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class MovieModel implements Parcelable {

    private int id;
    private String title;
    private String genres;
    private String overview;
    private String backdrop_path;
    private String budget;
    private String poster_path;
    private String release_date;
    private String revenue;
    private String runtime;
    private String status;
    private String name;
    private String profile_path;

    protected MovieModel(Parcel in) {
        id = in.readInt();
        title = in.readString();
        genres = in.readString();
        overview = in.readString();
        backdrop_path = in.readString();
        budget = in.readString();
        poster_path = in.readString();
        release_date = in.readString();
        revenue = in.readString();
        runtime = in.readString();
        status = in.readString();
        name = in.readString();
        profile_path = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenres() {
        return genres;
    }

    public void setGenres(String genres) {
        this.genres = genres;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getRevenue() {
        return revenue;
    }

    public void setRevenue(String revenue) {
        this.revenue = revenue;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile_path() {
        return profile_path;
    }

    public void setProfile_path(String profile_path) {
        this.profile_path = profile_path;
    }

    public MovieModel(){}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(title);
        parcel.writeString(genres);
        parcel.writeString(overview);
        parcel.writeString(backdrop_path);
        parcel.writeString(budget);
        parcel.writeString(poster_path);
        parcel.writeString(release_date);
        parcel.writeString(revenue);
        parcel.writeString(runtime);
        parcel.writeString(status);
        parcel.writeString(name);
        parcel.writeString(profile_path);
    }
}
