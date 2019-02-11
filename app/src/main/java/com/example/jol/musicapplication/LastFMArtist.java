package com.example.jol.musicapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LastFMArtist implements Parcelable {
    String name;
    String listeners;
    String mbid;
    String url;
    String streamable;
    ArrayList<ArtistImage> image;

    protected LastFMArtist(Parcel in) {
        name = in.readString();
        listeners = in.readString();
        mbid = in.readString();
        url = in.readString();
        streamable = in.readString();
    }

    public static final Creator<LastFMArtist> CREATOR = new Creator<LastFMArtist>() {
        @Override
        public LastFMArtist createFromParcel(Parcel in) {
            return new LastFMArtist(in);
        }

        @Override
        public LastFMArtist[] newArray(int size) {
            return new LastFMArtist[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(listeners);
        dest.writeString(mbid);
        dest.writeString(url);
        dest.writeString(streamable);
    }
}
