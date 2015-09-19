package com.ylj.movies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.parser.Trailer;
import com.parser.TrailerParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ylj on 15-9-16.
 */
public class TrailerLoader {

    Context context;
    Trailer[] trailers;
    ITrailerLoad onTralierLoadComplete;

    public TrailerLoader(Context context) {
        this.context = context;
    }

    public Trailer[] getTrailers() {
        return trailers;
    }

    public void Load(int movieId)
    {
        new TrailerDiscover()
                .execute(String.valueOf(movieId), context.getString(R.string.tmdb_key));
    }

    public void setOnTralierLoadComplete(ITrailerLoad onTralierLoadComplete) {
        this.onTralierLoadComplete = onTralierLoadComplete;
    }

    interface ITrailerLoad {
        void TrailerLoadComplete();
    }

    class TrailerDiscover extends AsyncTask<String, Void, String> {
        String baseUrl = "http://api.themoviedb.org/3/movie";
        String pathVideo = "videos";

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String key = params[1];
            Uri.Builder builder = Uri.parse(baseUrl).buildUpon()
                    .appendPath(id)
                    .appendPath(pathVideo)
                    .appendQueryParameter("api_key", key);
            Uri uri = builder.build();
            String jsonStr = null;
            HttpURLConnection urlConnection = null;
            try {
                URL url = new URL(uri.toString());
                urlConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                StringBuilder stringBuilder = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line);
                }
                jsonStr = stringBuilder.toString();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonStr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                List<Trailer> trailerList = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(s);
                int id = jsonObject.getInt("id");
                TrailerParser parser = new TrailerParser();
                JSONArray jsonArray = jsonObject.getJSONArray("results");
                for (int i = 0; i != jsonArray.length(); ++i) {
                    JSONObject trailerObj = jsonArray.getJSONObject(i);
                    Trailer trailer = parser.Parse(trailerObj.toString());
                    trailerList.add(trailer);
                }
                Trailer[] arrTrailers = new Trailer[trailerList.size()];
                trailers = trailerList.toArray(arrTrailers);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(onTralierLoadComplete != null)
            {
                onTralierLoadComplete.TrailerLoadComplete();
            }
        }
    }
}
