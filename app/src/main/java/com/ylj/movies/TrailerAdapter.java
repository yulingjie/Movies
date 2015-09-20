package com.ylj.movies;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parser.Trailer;

import java.nio.ReadOnlyBufferException;
import java.util.zip.Inflater;

/**
 * Created by ylj on 15-9-19.
 */
public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    Trailer[] mTrailers;
    OnTrailerClickListener mListener;

    public TrailerAdapter() {

    }
    public void setTrailers(Trailer[] trailers)
    {
        mTrailers = trailers;
        notifyDataSetChanged();
    }

    public void setOnTrailerClickListener(OnTrailerClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.trailer_item_view, parent, false);

        WindowManager windowManager = (WindowManager)parent.getContext().getSystemService(Context.WINDOW_SERVICE);

        int width = windowManager.getDefaultDisplay().getWidth();

        view.setLayoutParams(new RecyclerView.LayoutParams(width,RecyclerView.LayoutParams.WRAP_CONTENT));

        ViewHolder vh = new ViewHolder((RelativeLayout) view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Trailer trailer = mTrailers[position];
        holder.setTitle(trailer.getName());
        holder.setOnPlayClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.OnTrailerClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mTrailers != null) {
            return mTrailers.length;
        }
        return 0;
    }


    interface OnTrailerClickListener {
        void OnTrailerClick(int pos);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout mParent;
        ImageButton mBtnPlay;
        TextView mTrailerTitle;

        public ViewHolder(RelativeLayout parent) {
            super(parent);

            mBtnPlay = (ImageButton) parent.findViewById(R.id.button_play);
            mTrailerTitle = (TextView) parent.findViewById(R.id.trailer_title);
        }

        public void setTitle(String title) {
            mTrailerTitle.setText(title);
        }

        public void setOnPlayClick(View.OnClickListener onClickListener) {
            mBtnPlay.setOnClickListener(onClickListener);
        }
    }
}
