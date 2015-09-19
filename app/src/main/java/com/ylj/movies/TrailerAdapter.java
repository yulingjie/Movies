package com.ylj.movies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.parser.Trailer;

import java.util.zip.Inflater;

/**
 * Created by ylj on 15-9-19.
 */
public class TrailerAdapter extends BaseAdapter {

    Trailer[] mTrailers;
    Context mContext;
    OnTrailerClickListener mListener;
    public TrailerAdapter(Context context)
    {
        mContext = context;
    }
    public void setTrailers(Trailer[] trailers)
    {
        mTrailers = trailers;
        notifyDataSetInvalidated();
    }
    public void setOnTrailerClickListener(OnTrailerClickListener listener) {
        this.mListener = mListener;
    }

    @Override
    public int getCount() {
        if(mTrailers != null) {
            return mTrailers.length;
        }
        return 0;

    }

    @Override
    public Object getItem(int position) {
        return mTrailers[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if(view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            view = inflater.inflate(R.layout.trailer_item_view, null);
        }
        Trailer trailer = mTrailers[position];
        if(trailer != null) {
            TextView textView = (TextView) view.findViewById(R.id.trailer_title);
            textView.setText(trailer.getName());
            ImageButton imageButton = (ImageButton) view.findViewById(R.id.button_play);
            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener != null)
                    {
                        mListener.OnTrailerClick(position);
                    }
                }
            });
        }

        return view;
    }


    interface OnTrailerClickListener
    {
        void OnTrailerClick(int pos);
    }
}
