package com.ylj.movies;

import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parser.Trailer;
import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends ActionBarActivity {

    public static String KEY_MOVIE = "com.ylj.movies.MovieDetailActivity.movie";

    TrailerLoader loader;
    Trailer[] trailers;
    TrailerAdapter trailerAdapter;

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
        voteCount.setText(String.format("(%1$d votes)", movie.getVoteCount()));
        TextView vote = (TextView)findViewById(R.id.movie_vote);
        vote.setText(String.valueOf(movie.getVoteRate()));
        TextView releaseDate = (TextView)findViewById(R.id.movie_release_data);
        releaseDate.setText(movie.getReleaseDate());
        TextView plot = (TextView)findViewById(R.id.movie_plot);
        plot.setText(movie.getPlotSnippets());

        ListView listView = (ListView)findViewById(R.id.trailer_container);
        trailerAdapter = new TrailerAdapter(this);
        trailerAdapter.setOnTrailerClickListener(new OnTrailerClick());
        listView.setAdapter(trailerAdapter);

        loader = new TrailerLoader(this);
        loader.setOnTralierLoadComplete(new OnTrailerLoadComplete());
        loader.Load(movie.getId());
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
    class OnTrailerLoadComplete implements TrailerLoader.ITrailerLoad
    {

        @Override
        public void TrailerLoadComplete() {
            trailers = loader.getTrailers();
            trailerAdapter.setTrailers(trailers);
        }
    }

    class OnTrailerClick implements TrailerAdapter.OnTrailerClickListener{

        @Override
        public void OnTrailerClick(int pos) {
            Toast.makeText(MovieDetailActivity.this, String.valueOf(pos) ,Toast.LENGTH_SHORT).show();
        }
    }
}
