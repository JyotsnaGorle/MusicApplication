package com.example.jol.musicapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jol.musicapplication.Models.SavedAlbum;
import com.example.jol.musicapplication.fragments.AlbumOverviewFragment;

import java.util.ArrayList;

public class SavedArtistViewAdapter extends RecyclerView.Adapter<SavedArtistViewAdapter.ViewHolder>  {

    private ArrayList<SavedAlbum> savedAlbumList;
    Context context;

    public SavedArtistViewAdapter(Context context, ArrayList<SavedAlbum> savedAlbumList){
        this.savedAlbumList = savedAlbumList;
        this.context = context;
    }

    @NonNull
    @Override
    public SavedArtistViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem= layoutInflater.inflate(R.layout.artist_list_item, viewGroup, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final SavedAlbum currentSavedAlbum = savedAlbumList.get(i);
        viewHolder.imageView.setImageBitmap(currentSavedAlbum.image);
        viewHolder.textView.setText(currentSavedAlbum.name);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchOverviewFragment(currentSavedAlbum);
            }
        });
    }

    private void launchOverviewFragment(SavedAlbum currentSavedAlbum) {
        FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        Bundle args = new Bundle();
        args.putParcelable("saved_album", currentSavedAlbum);
        AlbumOverviewFragment albumOverviewFragment = new AlbumOverviewFragment();
        albumOverviewFragment.setArguments(args);
        ft.replace(R.id.fragment_container_2, albumOverviewFragment, "saved_album_overview");
        ft.addToBackStack("saved_album_overview");
        ft.commit();
    }

    @Override
    public int getItemCount() {
        return savedAlbumList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        ViewHolder(View itemView) {
            super(itemView);
            this.imageView = itemView.findViewById(R.id.artist_image);
            this.textView = itemView.findViewById(R.id.artist_name);
        }
    }
}
