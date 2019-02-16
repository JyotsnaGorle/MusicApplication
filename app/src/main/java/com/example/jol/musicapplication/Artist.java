package com.example.jol.musicapplication;

import android.graphics.Bitmap;
import android.widget.ImageView;

public class Artist {
    int id;
    String name;
    String listeners;
    String url;
    Bitmap image;

    Artist(int id, String name, String listeners, String url, Bitmap image) {
        this.id = id;
        this.name = name;
        this.listeners = listeners;
        this.url = url;
        this.image = image;
    }

    public String getName() {
        return this.name;
    }
}
