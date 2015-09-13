package com.ylj.movies;

import android.net.Uri;

import com.parser.Movie;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ylj on 15-9-13.
 */
public class MovieStorage {

    final static String baseUrl = "http://image.tmdb.org/t/p/";
    MovieParcelable[] mMovies;

    public MovieParcelable getMovie(int pos)
    {
        if(pos<0 || pos >= mMovies.length)
        {
            return null;
        }
        return mMovies[pos];
    }

    public void setMovies(Movie[] movies)
    {
        List<MovieParcelable> moviesParcelable = new ArrayList<>();
        for(Movie movie : movies)
        {
            MovieParcelable movieParcelable = new MovieParcelable();
            movieParcelable.setTitle(movie.getTitle());
            movieParcelable.setImgUrl(movie.getBackdrop_path());
            movieParcelable.setPlotSnippets(movie.getOverview());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            movieParcelable.setReleaseDate(dateFormat.format(movie.getReleaseDate()));
            movieParcelable.setVoteCount(movie.getVote_count());
            movieParcelable.setVoteRate(movie.getVode_average());
            moviesParcelable.add(movieParcelable);
        }
        MovieParcelable[] arrMovies = new MovieParcelable[movies.length];
        mMovies = moviesParcelable.toArray(arrMovies);
    }
    public String[] getMovieImageUrls(String size)
    {
        List<String> imgUrls = new ArrayList<>();
        for(MovieParcelable movie : mMovies)
        {
            if(movie.getImgUrl() != null
                    && movie.getImgUrl().length() > 0
                    && !movie.getImgUrl().equals("null")) {
                Uri.Builder builder = Uri.parse(baseUrl).buildUpon()
                        .appendPath(size)
                        .appendEncodedPath(movie.getImgUrl());
                imgUrls.add(builder.build().toString());
            }
        }
        String[] arrImgUrls = new String[mMovies.length];
        return imgUrls.toArray(arrImgUrls);
    }
    public String getMovieImageUrl(int pos,String size)
    {
        if(pos <0 || pos >= mMovies.length) return "";
        MovieParcelable movie = mMovies[pos];
        return getMovieImageUrl(movie, size);
    }
    public String getMovieImageUrl(MovieParcelable movie, String size)
    {
        if(movie.getImgUrl() != null
                && movie.getImgUrl().length() > 0
                && !movie.getImgUrl().equals("null")) {
            Uri.Builder builder = Uri.parse(baseUrl).buildUpon()
                    .appendPath(size)
                    .appendEncodedPath(movie.getImgUrl());
            return builder.build().toString();
        }
        return "";
    }

    private static MovieStorage mInstance;
    public static MovieStorage getInstance() {
        if(mInstance == null)
        {
            mInstance = new MovieStorage();
        }
        return mInstance;
    }
}
