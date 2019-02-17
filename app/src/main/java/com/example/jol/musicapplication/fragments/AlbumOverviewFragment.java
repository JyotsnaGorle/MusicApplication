package com.example.jol.musicapplication.fragments;

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

import com.example.jol.musicapplication.Database.DBDeleteCompletion;
import com.example.jol.musicapplication.Database.DBSaveCompletion;
import com.example.jol.musicapplication.Database.SQLiteDatabaseHandler;
import com.example.jol.musicapplication.Models.Album;
import com.example.jol.musicapplication.Models.SavedAlbum;
import com.example.jol.musicapplication.R;

import java.io.InputStream;

public class AlbumOverviewFragment extends Fragment implements DBSaveCompletion {
    TextView playcount;
    TextView albumName;
    TextView name;
    ImageView image;
    Button saveButton;
    TextView onSave;
    Button deleteButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_overview,
                container, false);
        playcount = view.findViewById(R.id.playcount);
        name = view.findViewById(R.id.artist_overview_name);
        albumName = view.findViewById(R.id.album_name);
        image = view.findViewById(R.id.artist_overview_image);
        saveButton = view.findViewById(R.id.save_button);
        onSave = view.findViewById(R.id.onSave);
        deleteButton = view.findViewById(R.id.delete_button);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        final SQLiteDatabaseHandler dbInstance = SQLiteDatabaseHandler.getInstance(getContext());
        dbInstance.saveCompletion = this;

        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        supportActionBar.setTitle("Overview");
        supportActionBar.setDisplayShowHomeEnabled(true);
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        if(getArguments().get("album") != null) {
            Album album = (Album) getArguments().get("album");
            name.setText(album.artist.name);
            playcount.setText(album.playcount);
            albumName.setText(album.name);
            final String finalUrl = album.image.get(3).text;
            if(image != null) {
                new DownloadImageAsyncTask(image).execute(finalUrl);
            }
            saveButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    new DownloadImageAsyncTask(dbInstance).execute(finalUrl);
                }
            });
        }
        else if(getArguments().get("saved_album") != null) {
            final SavedAlbum savedAlbum = (SavedAlbum) getArguments().get("saved_album");
            name.setText(savedAlbum.getArtistName());
            playcount.setText(savedAlbum.getPlaycount());
            image.setImageBitmap(savedAlbum.image);
            albumName.setText(savedAlbum.name);
            saveButton.setVisibility(View.GONE);
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    dbInstance.deleteCompletion = (DBDeleteCompletion) getActivity();
                    dbInstance.deleteOne(savedAlbum);
                }
            });
        }

    }

    @Override
    public void onSave() {
        saveButton.setVisibility(View.GONE);
        onSave.setText("Album is saved locally, view it on your home screen");
        onSave.setVisibility(View.VISIBLE);
    }

    @SuppressLint("StaticFieldLeak")
    private class DownloadImageAsyncTask extends AsyncTask<String,Void,Bitmap> {
        SQLiteDatabaseHandler dbInstance;
        ImageView imageView;
        public DownloadImageAsyncTask(SQLiteDatabaseHandler dbInstance) {
            this.dbInstance = dbInstance;
        }

        public DownloadImageAsyncTask(ImageView image) {
            this.imageView = image;
        }

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
            if(result == null){
                result = BitmapFactory.decodeResource(getResources(), R.drawable.icons8);
            }
            if(dbInstance != null) {
                SavedAlbum savedAlbum = new SavedAlbum(1, albumName.getText().toString(), playcount.getText().toString(), name.getText().toString(), result);
                dbInstance.saveAlbum(savedAlbum);
            }
            if(imageView != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}
