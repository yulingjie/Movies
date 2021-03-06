package com.ylj.movies;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ylj on 7/20/15.
 */
public class ImageAdapter extends BaseAdapter {


    private Context context;
    private String[] imageUrls;


    public void setImageUrls(String[] imageUrls) {

        this.imageUrls = imageUrls;

        notifyDataSetChanged();
    }



    public ImageAdapter(Context c)
    {
        context = c;
    }

    @Override
    public int getCount() {
        if(imageUrls == null || imageUrls.length <= 0) {
            return mThumbIds.length;
        }
        return imageUrls.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        ImageView imageView;
        if(convertView == null){
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(3,3,3,3);
        }
        else {
            imageView = (ImageView) convertView;
        }
        if((imageUrls == null) || (position >= imageUrls.length)) {
            imageView.setImageResource(mThumbIds[position]);
        }
        else if(imageUrls[position] == null || imageUrls[position].length() <= 0) {
            imageView.setImageResource(mThumbIds[position]);
        }
        else {
            Picasso.with(context).load(imageUrls[position]).into(imageView);
        }

        return imageView;
    }
    private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
}
