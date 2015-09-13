package com.ylj.movies;

import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends ActionBarActivity {

    public static String KEY_MOVIE = "com.ylj.movies.MovieDetailActivity.movie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        Bundle b = getIntent().getExtras();
        MovieParcelable movie = b.getParcelable(KEY_MOVIE);
        ImageView imageView = (ImageView)findViewById(R.id.movie_image);
        Picasso.with(this).load(MovieStorage.getInstance().getMovieImageUrl(movie,"w185")).into(imageView);
        TextView textView = (TextView)findViewById(R.id.movie_title);
        textView.setText(movie.getTitle());
        TextView voteCount = (TextView)findViewById(R.id.movie_vote_count);
        voteCount.setText(String.format("(%1$d votes)",movie.getVoteCount()));
        TextView vote = (TextView)findViewById(R.id.movie_vote);
        vote.setText(String.valueOf(movie.getVoteRate()));
        TextView releaseDate = (TextView)findViewById(R.id.movie_release_data);
        releaseDate.setText(movie.getReleaseDate());
        TextView plot = (TextView)findViewById(R.id.movie_plot);
        plot.setText(movie.getPlotSnippets());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
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
}
