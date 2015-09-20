package com.ylj.movies;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


import com.parser.Trailer;
import com.squareup.picasso.Picasso;


public class MovieDetailActivity extends ActionBarActivity {

    public static String KEY_MOVIE = "com.ylj.movies.MovieDetailActivity.movie";

    TrailerLoader loader;
    Trailer[] trailers;
    TrailerAdapter mTrailerAdapter;
    RecyclerView mRecyclerView;
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


        mRecyclerView = (RecyclerView)findViewById(R.id.recycler_view_trailers);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mTrailerAdapter = new TrailerAdapter();
        mTrailerAdapter.setOnTrailerClickListener(new OnTrailerClick());
        mRecyclerView.setAdapter(mTrailerAdapter);

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
            mTrailerAdapter.setTrailers(trailers);
        }
    }

    class OnTrailerClick implements TrailerAdapter.OnTrailerClickListener{

        @Override
        public void OnTrailerClick(int pos) {
            Trailer trailer = trailers[pos];
            watchYoutubeVideo(trailer.getId());
        }
    }

    /**
     * watch youtube video function
     * copy from [ref](http://stackoverflow.com/questions/574195/android-youtube-app-play-video-intent)
     */
    void watchYoutubeVideo(String id)
    {
        try{
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
            startActivity(intent);
        }catch (ActivityNotFoundException ex){
            Intent intent=new Intent(Intent.ACTION_VIEW,
                    Uri.parse("http://www.youtube.com/watch?v="+id));
            startActivity(intent);
        }
    }
}
