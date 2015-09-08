package com.ylj.movies;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.parser.Movie;

import java.util.ArrayList;
import java.util.List;


public class HelloGridActivity extends ActionBarActivity {


    MovieLoader movieLoader;
    ImageAdapter imageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello_grid);

        imageAdapter = new ImageAdapter(this);

        GridView gridView = (GridView) findViewById(R.id.gridview);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HelloGridActivity.this, "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        movieLoader = new MovieLoader(this);
        movieLoader.setCallback(new OnMovieLoadComplete());
        movieLoader.Load();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_hello_grid, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    class OnMovieLoadComplete implements IMovieLoad
    {
        @Override
        public void MovieLoadComplete() {
            Movie[] movies = movieLoader.getMovies();
            List<String> moviePath = new ArrayList<>();
            for(Movie movie : movies)
            {
                moviePath.add(movie.getBackdrop_path());
            }
            String[] path = new String[moviePath.size()];

            imageAdapter.setImagePaths(moviePath.toArray(path));
        }
    }

}
