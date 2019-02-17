package com.example.jol.musicapplication.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.jol.musicapplication.Models.SavedAlbum;
import com.example.jol.musicapplication.R;
import com.example.jol.musicapplication.Database.SQLiteDatabaseHandler;
import com.example.jol.musicapplication.SavedArtistViewAdapter;

import java.util.ArrayList;

public class SavedAlbumListFragment extends Fragment {

    private RecyclerView savedAlbums;
    private SQLiteDatabaseHandler db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saved_album,
                container, false);

        savedAlbums = view.findViewById(R.id.saved_albums);
        savedAlbums.setHasFixedSize(true);
        db = SQLiteDatabaseHandler.getInstance(this.getContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ArrayList<SavedAlbum> allMyMusic = db.allMyMusic();
        if (allMyMusic.size() > 0 )
            populateList(allMyMusic);
    }

    private void populateList(ArrayList<SavedAlbum> savedSavedAlbums) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this.getContext());
        this.savedAlbums.setLayoutManager(mLayoutManager);
        this.savedAlbums.setAdapter(new SavedArtistViewAdapter(this.getContext(), savedSavedAlbums));
    }
}
