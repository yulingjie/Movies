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
import android.widget.GridView;

import com.parser.Movie;

import java.util.ArrayList;
import java.util.List;


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
        if(mDefaultPref.contains(SettingsActivity.KEY_PREF_IMAGE_SIZE))
        {
            imageAdapter.setSize(mDefaultPref.getString(SettingsActivity.KEY_PREF_IMAGE_SIZE, getString(R.string.pref_img_size_default)));
        }
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
            List<String> moviePath = new ArrayList<>();
            for(Movie movie : movies)
            {
                if(!movie.getBackdrop_path().equals("null")) {
                    moviePath.add(movie.getBackdrop_path());
                }
                else
                {
                    moviePath.add("");
                }
            }
            String[] path = new String[moviePath.size()];

            imageAdapter.setImagePaths(moviePath.toArray(path));
        }
    }

    class OnSharedPreferenceChange implements SharedPreferences.OnSharedPreferenceChangeListener
    {

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            if(key.equals(SettingsActivity.KEY_PREF_IMAGE_SIZE))
            {
                mOldImageSize = sharedPreferences.getString(key,mOldImageSize);
                imageAdapter.setSize(mOldImageSize);
            }
            else if(key.equals(SettingsActivity.KEY_PREF_SORT_BY))
            {
                String sort_by = sharedPreferences.getString(key, getString(R.string.pref_sort_by_default_value));
                movieLoader.Load(sort_by);
            }

        }
    }
}
