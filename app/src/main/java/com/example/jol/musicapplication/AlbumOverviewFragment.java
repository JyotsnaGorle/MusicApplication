package com.example.jol.musicapplication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class AlbumOverviewFragment extends Fragment {
    TextView listeners;
    TextView name;
    ImageView image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_album_overview,
                container, false);
        listeners = view.findViewById(R.id.number_listeners);
        name = view.findViewById(R.id.artist_overview_name);
        image = view.findViewById(R.id.artist_overview_image);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        ActionBar supportActionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        supportActionBar.setTitle("Overview");
        supportActionBar.setDisplayHomeAsUpEnabled(true);
        LastFMArtist artist = (LastFMArtist) getArguments().get("artist");
        name.setText(artist.name);
        listeners.setText(artist.listeners);
        String url = artist.image.get(2).text;
        if(image != null) {
            new DownloadImageTask(image).execute(url);
        }
    }
}
