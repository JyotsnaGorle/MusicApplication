package com.example.jol.musicapplication;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class LastFMArtistsResposne implements Parcelable {
    ArrayList<LastFMArtist> artist;

    protected LastFMArtistsResposne(Parcel in) {
        artist = in.createTypedArrayList(LastFMArtist.CREATOR);
    }

    public static final Creator<LastFMArtistsResposne> CREATOR = new Creator<LastFMArtistsResposne>() {
        @Override
        public LastFMArtistsResposne createFromParcel(Parcel in) {
            return new LastFMArtistsResposne(in);
        }

        @Override
        public LastFMArtistsResposne[] newArray(int size) {
            return new LastFMArtistsResposne[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(artist);
    }
}
