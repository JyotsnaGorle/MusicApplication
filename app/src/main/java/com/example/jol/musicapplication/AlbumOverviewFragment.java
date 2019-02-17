package com.example.jol.musicapplication;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

public class AlbumOverviewFragment extends Fragment {
    TextView playcount;
    TextView name;
    ImageView image;
    Button saveButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_overview,
                container, false);
        playcount = view.findViewById(R.id.number_listeners);
        name = view.findViewById(R.id.artist_overview_name);
        image = view.findViewById(R.id.artist_overview_image);
        saveButton = view.findViewById(R.id.save_button);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        supportActionBar.setTitle("Overview");
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        if(getArguments().get("album") != null) {
            Album album = (Album) getArguments().get("album");
            name.setText(album.name);
            playcount.setText(album.playcount);
            String url = album.image.get(3).text;

            if(image != null) {
                new DownloadImageTask(image).execute(url);
            }
            final String finalUrl = url;
            saveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    new DownloadImageAndSaveToDB().execute(finalUrl);
                }
            });
        }
        else if(getArguments().get("saved_album") != null) {
            SavedAlbum savedaAlbum = (SavedAlbum) getArguments().get("saved_album");
            name.setText(savedaAlbum.name);
            playcount.setText(savedaAlbum.playcount);
            image.setImageBitmap(savedaAlbum.image);
            saveButton.setVisibility(View.INVISIBLE);
        }

    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageAndSaveToDB extends AsyncTask<String,Void,Bitmap> {

        @Override
        protected Bitmap doInBackground(String... strings) {
            String urldisplay = strings[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            SavedAlbum savedAlbum = new SavedAlbum(1, name.getText().toString(), playcount.getText().toString(), "", result);
            SQLiteDatabaseHandler.getInstance(getContext()).saveAlbum(savedAlbum);
        }
    }
}
