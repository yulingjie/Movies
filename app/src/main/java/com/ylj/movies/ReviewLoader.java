package com.ylj.movies;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import com.parser.Review;
import com.parser.ReviewParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ylj on 15-9-28.
 */
public class ReviewLoader {

    Review[] mReviews;
    IReviewLoad mReviewLoadListener;
    Context mContext;
    public ReviewLoader(Context context)
    {
        mContext = context;
    }

    public Review[] getReviews() {
        return mReviews;
    }

    public void setReviewLoadListener(IReviewLoad listener) {
        mReviewLoadListener = listener;
    }
    public void Load(int id, int page)
    {
        new ReviewDiscover().execute(String.valueOf(id), mContext.getString(R.string.tmdb_key), String.valueOf(page) );
    }

    interface IReviewLoad {
        void ReviewLoadComplete();
    }

    class ReviewDiscover extends AsyncTask<String, Void, String> {
        String baseUrl = "http://api.themoviedb.org/3/movie";
        String pathReview = "reviews";

        @Override
        protected String doInBackground(String... params) {
            String id = params[0];
            String key = params[1];
            String numPage = params[2];
            Uri.Builder builder = Uri.parse(baseUrl).buildUpon()
                    .appendPath(id)
                    .appendPath(pathReview)
                    .appendQueryParameter("api_key", key)
                    .appendQueryParameter("page", numPage);
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

            try {
                JSONObject jsonObject = new JSONObject(s);
                int id = jsonObject.getInt("id");
                int page = jsonObject.getInt("page");
                List<Review> reviews = new ArrayList<>();
                JSONArray results = jsonObject.getJSONArray("results");
                ReviewParser parser = new ReviewParser();
                for (int i = 0; i != results.length(); ++i) {
                    reviews.add(parser.Parse(results.getJSONObject(i).toString()));
                }
                mReviews = new Review[reviews.size()];
                mReviews = reviews.toArray(mReviews);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (mReviewLoadListener != null) {
                mReviewLoadListener.ReviewLoadComplete();
            }
        }
    }
}
