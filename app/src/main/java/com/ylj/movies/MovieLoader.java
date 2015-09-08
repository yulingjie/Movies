package com.ylj.movies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.parser.IParser;
import com.parser.Movie;
import com.parser.Page;
import com.parser.PageParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by ylj on 8/6/15.
 */

interface IMovieLoad
{
    void MovieLoadComplete();
}

public class MovieLoader {
    Movie[] movies;
    Context mContext;
    IMovieLoad callback;

    public Movie[] getMovies() {
        return movies;
    }

    public MovieLoader(Context context)
    {
        mContext = context;
    }

    public void setCallback(IMovieLoad callback) {
        this.callback = callback;
    }

    public void Load()
    {
        MovieDiscover movieDiscover = new MovieDiscover();
        movieDiscover.execute("popularity.desc", mContext.getString(R.string.tmdb_key));
    }

    class MovieDiscover extends AsyncTask<String,Void, String>
    {
        String _url = "http://api.themoviedb.org/3/discover/movie?";
        @Override
        protected String doInBackground(String... params) {

            String jsonStr = null;
            String sort_by = params[0];
            String api_key = params[1];
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("discover")
                    .appendPath("movie")
                    .appendQueryParameter("sort_by",sort_by)
                    .appendQueryParameter("api_key", api_key);
            Uri uri = builder.build();
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(uri.toString());
                urlConnection = (HttpURLConnection)url.openConnection();
                InputStream instream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(instream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line ;
                while((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                jsonStr = stringBuilder.toString();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return jsonStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            IParser<Page> pageParser = new PageParser();
            Page p = pageParser.Parse(s);
            movies = p.getMovies();

            if(callback != null) {
                callback.MovieLoadComplete();
            }

        }
    }
}
