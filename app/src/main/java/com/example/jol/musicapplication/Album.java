package com.example.jol.musicapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Album implements Parcelable {
    String name;
    String playcount;
    String mbid;
    String url;
    ArrayList<AlbumImage> image;
    ArtistFM artist;

    protected Album(Parcel in) {
        name = in.readString();
        playcount = in.readString();
        mbid = in.readString();
        url = in.readString();
    }

    public static final Creator<Album> CREATOR = new Creator<Album>() {
        @Override
        public Album createFromParcel(Parcel in) {
            return new Album(in);
        }

        @Override
        public Album[] newArray(int size) {
            return new Album[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(playcount);
        dest.writeString(mbid);
        dest.writeString(url);
    }
}
