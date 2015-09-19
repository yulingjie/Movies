package com.ylj.movies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ylj on 15-9-16.
 */
public class TrailerParcelable implements Parcelable{
    String mKey;

    public String getKey() {
        return mKey;
    }

    public void setKey(String key) {
        this.mKey = key;
    }

    public TrailerParcelable(Parcel in)
    {
        mKey = in.readString();
    }
    public TrailerParcelable()
    {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mKey);
    }

    public static final Parcelable.Creator<TrailerParcelable> CREATOR
            = new Parcelable.Creator<TrailerParcelable>() {
        public TrailerParcelable createFromParcel(Parcel in) {
            return new TrailerParcelable(in);
        }

        public TrailerParcelable[] newArray(int size) {
            return new TrailerParcelable[size];
        }
    };
}
