package com.ylj.movies;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.parser.Movie;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class MainFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";



    private String mOldImageSize;


    MovieLoader movieLoader;
    ImageAdapter imageAdapter;
    private OnFragmentInteractionListener mListener;
    private SharedPreferences mDefaultPref;
    private SharedPreferences.OnSharedPreferenceChangeListener mOnPrefChangeListener;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        mOnPrefChangeListener = new OnSharedPreferenceChange();
        mDefaultPref = PreferenceManager.getDefaultSharedPreferences(this.getActivity());

        imageAdapter = new ImageAdapter(this.getActivity());
        mOldImageSize = mDefaultPref.getString(SettingsActivity.KEY_PREF_IMAGE_SIZE, getString(R.string.pref_img_size_default));

        movieLoader = new MovieLoader(this.getActivity());
        movieLoader.setCallback(new OnMovieLoadComplete());
        if(mDefaultPref.contains(SettingsActivity.KEY_PREF_SORT_BY)) {
            movieLoader.Load(mDefaultPref.getString(SettingsActivity.KEY_PREF_SORT_BY,getString(R.string.pref_sort_by_default_value)));
        }
        else {
            movieLoader.Load();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mDefaultPref.unregisterOnSharedPreferenceChangeListener(mOnPrefChangeListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        mDefaultPref.registerOnSharedPreferenceChangeListener(mOnPrefChangeListener);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_main, container, false);
        GridView gridView = (GridView) view.findViewById(R.id.gridview);
        gridView.setAdapter(imageAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Movie movie = mMovies[position];
                Bundle b = new Bundle();
                MovieParcelable movie = MovieStorage.getInstance().getMovie(position);
                b.putParcelable(MovieDetailActivity.KEY_MOVIE, movie);
                Intent intent = new Intent(MainFragment.this.getActivity(), MovieDetailActivity.class);
                intent.putExtras(b);
                startActivity(intent);
            }
        });


        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_main_fragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class OnMovieLoadComplete implements IMovieLoad
    {
        @Override
        public void MovieLoadComplete() {
            Movie[] movies = movieLoader.getMovies();
            MovieStorage.getInstance().setMovies(movies);

            imageAdapter.setImageUrls(MovieStorage.getInstance().getMovieImageUrls(mOldImageSize));
        }
    }

    class OnSharedPreferenceChange implements SharedPreferences.OnSharedPreferenceChangeListener
    {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(SettingsActivity.KEY_PREF_IMAGE_SIZE))
            {
                mOldImageSize = sharedPreferences.getString(key,mOldImageSize);
                imageAdapter.setImageUrls(MovieStorage.getInstance().getMovieImageUrls(mOldImageSize));
            }
            else if(key.equals(SettingsActivity.KEY_PREF_SORT_BY))
            {
                String sort_by = sharedPreferences.getString(key, getString(R.string.pref_sort_by_default_value));
                movieLoader.Load(sort_by);
            }

        }
    }
}
