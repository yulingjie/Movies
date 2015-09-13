package com.parser;

import java.util.Date;

/**
 * Created by ylj on 7/27/15.
 * {"adult":false,
 *  "backdrop_path":"/dkMD5qlogeRMiEixC4YNPUvax2T.jpg",
 *  "genre_ids":[28,12,878,53],
 *  "id":135397,
 *  "original_language":"en",
 *  "original_title":"Jurassic World",
 *  "overview":"Twenty-two years after the events of Jurassic Park, Isla Nublar now features a fully functioning dinosaur theme park, Jurassic World, as originally envisioned by John Hammond.",
 *  "release_date":"2015-06-12",
 *  "poster_path":"/uXZYawqUsChGSj54wcuBtEdUJbh.jpg",
 *  "popularity":85.59593,
 *  "title":"Jurassic World",
 *  "video":false,"vote_average":7.0,"vote_count":1538}
 */
public class Movie {
    private boolean adult;
    private String backdrop_path;
    private int[] genre_ids;
    private int id;
    private String original_language;
    private String original_title;
    private String overview;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    private String posterPath;
    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    private Date  releaseDate;
    private float popularity;
    private String title;
    private boolean video;
    private float vode_average;
    private int vote_count;

    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public void setGenre_ids(int[] genre_ids) {
        this.genre_ids = genre_ids;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setOriginal_language(String original_language) {
        this.original_language = original_language;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setVideo(boolean video) {
        this.video = video;
    }

    public void setVode_average(float vode_average) {
        this.vode_average = vode_average;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public boolean isAdult() {
        return adult;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public int[] getGenre_ids() {
        return genre_ids;
    }

    public int getId() {
        return id;
    }

    public String getOriginal_language() {
        return original_language;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getTitle() {
        return title;
    }

    public boolean isVideo() {
        return video;
    }

    public float getVode_average() {
        return vode_average;
    }

    public int getVote_count() {
        return vote_count;
    }


}
