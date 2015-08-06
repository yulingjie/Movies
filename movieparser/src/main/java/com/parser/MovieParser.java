package com.parser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ylj on 7/29/15.
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
    public class MovieParser implements IParser<Movie> {

    @Override
    public Movie Parse(String jsonStr) {
        Movie movie = new Movie();
        try {
            JSONObject jsonObject = new JSONObject(jsonStr);
            movie.setAdult(jsonObject.getBoolean("adult"));
            movie.setBackdrop_path(jsonObject.getString("backdrop_path"));
            JSONArray genreIdsObject = jsonObject.getJSONArray("genre_ids");
            int[] genreIds = new int[genreIdsObject.length()];
            for(int i = 0; i != genreIdsObject.length(); ++i)
            {
                genreIds[i] = genreIdsObject.getInt(i);
            }
            movie.setGenre_ids(genreIds);
            movie.setId(jsonObject.getInt("id"));
            movie.setOriginal_language(jsonObject.getString("original_language"));
            movie.setOriginal_title(jsonObject.getString("original_title"));
            movie.setOverview(jsonObject.getString("overview"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat();
            
            movie.setReleaseDate(new SimpleDateFormat("yyyy-MM-dd").parse(jsonObject.getString("release_date")));
            //movie.setReleaseDate(new Date(jsonObject.getString("release_date")));
            movie.setPosterPath(jsonObject.getString("poster_path"));
            movie.setPopularity((float)jsonObject.getDouble("popularity"));
            movie.setTitle(jsonObject.getString("title"));
            movie.setVideo(jsonObject.getBoolean("video"));
            movie.setVode_average((float)jsonObject.getDouble("vote_average"));
            movie.setVote_count(jsonObject.getInt("vote_count"));
            return movie;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
