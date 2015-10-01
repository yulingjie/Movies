package com.ylj.movies;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.parser.Review;

/**
 * Created by ylj on 15-9-28.
 */
public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder>{
    Review[] mReviews;

    public void setReviews(Review[] reviews)
    {
        mReviews = reviews;

        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_review, parent, false);
        ViewHolder vh = new ViewHolder((RelativeLayout)view);

        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Review review = mReviews[position];
        holder.setContent(review.getContent());
        holder.setAuthor(review.getAuthor());
    }

    @Override
    public int getItemCount() {
        if(mReviews == null) return 0;
        return mReviews.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView mContent;
        TextView mAuthor;
        public ViewHolder(RelativeLayout parent)
        {
            super(parent);
            mContent = (TextView)parent.findViewById(R.id.review_content);
            mAuthor = (TextView)parent.findViewById(R.id.review_author);
        }
        public void setContent(String content)
        {
            mContent.setText(content);
        }
        public void setAuthor(String author)
        {
            mAuthor.setText(author);
        }
    }
}
