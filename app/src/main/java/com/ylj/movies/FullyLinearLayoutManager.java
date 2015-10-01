package com.ylj.movies;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * Copy from @ref http://frank-zhu.github.io/android/2015/02/26/android-recyclerview-part-3/
 * modified and used by ylj to resolve reviews display problem
 */
public class FullyLinearLayoutManager extends LinearLayoutManager{

    private final static String TAG = "FullyLinearLayoutMgr";
    private int[] mMeasuredDimension = new int[2];

    public FullyLinearLayoutManager(Context context)
    {
        super(context);
    }

    @Override
    public void onMeasure(RecyclerView.Recycler recycler,
                          RecyclerView.State state,
                          int widthSpec,
                          int heightSpec) {
        final int widthMode = View.MeasureSpec.getMode(widthSpec);
        final int heightMode = View.MeasureSpec.getMode(heightSpec);
        final int widthSize = View.MeasureSpec.getSize(widthSpec);
        final int heightSize = View.MeasureSpec.getSize(heightSpec);

        Log.i(TAG, "onMeasure called." +
                "\n widthMode: " + widthMode
        +  "\n heightMode: " + heightMode
        +  "\n widthSize: " + widthSize
        +  "\n heightSize: " + heightSize
        +  "\ngetItemCount(): " + getItemCount());

        int width = 0;
        int height =0;
        for(int i = 0; i != getItemCount(); ++i)
        {
            measureScrapChild(recycler,i,
                    View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),
                    View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED),
                    mMeasuredDimension);
            if(getOrientation() == HORIZONTAL){
                width = width + mMeasuredDimension[0];
                if(i == 0)
                {
                    height = mMeasuredDimension[1];
                }
            } else{
                height = height + mMeasuredDimension[1];
                if(i == 0)
                {
                    width = mMeasuredDimension[0];
                }
            }
        }
        switch(widthMode)
        {
            case View.MeasureSpec.EXACTLY:
                width = widthSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }
        switch (heightMode)
        {
            case View.MeasureSpec.EXACTLY:
                height = heightSize;
            case View.MeasureSpec.AT_MOST:
            case View.MeasureSpec.UNSPECIFIED:
        }
        Log.i(TAG, "onMeasure end, width: "+ width
                + "\nheight: "+ height);
        setMeasuredDimension(width, height);
    }

    private void measureScrapChild(RecyclerView.Recycler recycler,
                                   int position,
                                   int widthSpec, int heightSpec,
                                   int[] measuredDimension) {
        View view = recycler.getViewForPosition(0);
        if (view != null) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();

            int childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec,
                    getPaddingLeft() + getPaddingRight(), params.width);

            int childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec,
                    getPaddingTop() + getPaddingBottom(), params.height);

            view.measure(childWidthSpec, childHeightSpec);
            measuredDimension[0] = view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
            measuredDimension[1] = view.getMeasuredHeight() + params.bottomMargin + params.topMargin;
            recycler.recycleView(view);
        }
    }
}
