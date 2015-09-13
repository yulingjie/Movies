package com.ylj.movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ylj on 15-9-13.
 */
public class MovieParcelable implements Parcelable {


    String title;
    String imgUrl;
    float voteRate;
    int voteCount;
    String releaseDate;
    String plotSnippets;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public float getVoteRate() {
        return voteRate;
    }

    public void setVoteRate(float voteRate) {
        this.voteRate = voteRate;
    }

    public int getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getPlotSnippets() {
        return plotSnippets;
    }

    public void setPlotSnippets(String plotSnippets) {
        this.plotSnippets = plotSnippets;
    }

    public MovieParcelable()
    {

    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(imgUrl);
        dest.writeFloat(voteRate);
        dest.writeInt(voteCount);
        dest.writeString(releaseDate);
        dest.writeString(plotSnippets);
    }
    private MovieParcelable(Parcel in)
    {
        title = in.readString();
        imgUrl = in.readString();
        voteRate = in.readFloat();
        voteCount = in.readInt();
        releaseDate = in.readString();
        plotSnippets = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }

    public static final Parcelable.Creator<MovieParcelable> CREATOR
            = new Parcelable.Creator<MovieParcelable>() {
        public MovieParcelable createFromParcel(Parcel in) {
            return new MovieParcelable(in);
        }

        public MovieParcelable[] newArray(int size) {
            return new MovieParcelable[size];
        }
    };
}
