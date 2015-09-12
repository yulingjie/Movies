package com.parser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ylj on 8/11/15.
 */
public class PageParser implements IParser<Page>
{
    @Override
    public Page Parse(String jsonStr) {
        Page page = new Page();
        JSONObject jsonObject = new JSONObject(jsonStr);
        page.setPage(jsonObject.getInt("page"));
        JSONArray moviesObject = jsonObject.getJSONArray("results");
        IParser<Movie> movieParser = new MovieParser();
        List<Movie> movies = new ArrayList<>();
        for(int i = 0; i != moviesObject.length();++i)
        {
            JSONObject movieObject = moviesObject.getJSONObject(i);
            Movie movie =movieParser.Parse(movieObject.toString());
            if(movie != null)
            {
                movies.add(movie);
            }
        }
        Movie[] arrMovie = new Movie[movies.size()];
        page.setMovies(movies.toArray(arrMovie));
        return page;
    }
}
