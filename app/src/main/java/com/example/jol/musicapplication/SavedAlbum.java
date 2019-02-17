package com.example.jol.musicapplication;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class SavedAlbum implements Parcelable {
    int id;
    String name;
    String playcount;
    String url;
    Bitmap image;

    SavedAlbum(int id, String name, String playcount, String url, Bitmap image) {
        this.id = id;
        this.name = name;
        this.playcount = playcount;
        this.url = url;
        this.image = image;
    }

    protected SavedAlbum(Parcel in) {
        id = in.readInt();
        name = in.readString();
        playcount = in.readString();
        url = in.readString();
        image = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<SavedAlbum> CREATOR = new Creator<SavedAlbum>() {
        @Override
        public SavedAlbum createFromParcel(Parcel in) {
            return new SavedAlbum(in);
        }

        @Override
        public SavedAlbum[] newArray(int size) {
            return new SavedAlbum[size];
        }
    };

    public String getName() {
        return this.name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(playcount);
        dest.writeString(url);
        dest.writeParcelable(image, flags);
    }
}
