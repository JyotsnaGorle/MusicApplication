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

import com.example.jol.musicapplication.Models.Album;
import com.example.jol.musicapplication.Models.LastFMArtistsResposne;
import com.example.jol.musicapplication.Service.DownloadImageTask;
import com.example.jol.musicapplication.fragments.AlbumOverviewFragment;

import java.util.ArrayList;

public class ArtistRecyclerViewAdapter extends RecyclerView.Adapter<ArtistRecyclerViewAdapter.ViewHolder>  {

    private ArrayList<Album> artistList;
    Context context;

    public ArtistRecyclerViewAdapter(Context context, LastFMArtistsResposne artistsResposne){
        this.artistList = artistsResposne.album;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem= layoutInflater.inflate(R.layout.artist_list_item, viewGroup, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, final int i) {
        final Album currentArtist = artistList.get(i);
        String url = currentArtist.image.get(1).text;
        DownloadImageTask downloadImageTask = null;
        if(viewHolder.imageView != null) {
            downloadImageTask = new DownloadImageTask(viewHolder.imageView);
            downloadImageTask.execute(url);
        }
        viewHolder.textView.setText(currentArtist.name);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((SearchActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putParcelable("album", currentArtist);
                AlbumOverviewFragment albumOverviewFragment = new AlbumOverviewFragment();
                albumOverviewFragment.setArguments(args);
                ft.replace(R.id.fragment_container, albumOverviewFragment, "album_overview");
                ft.addToBackStack("album_overview");
                ft.commit();
            }
        });
    }

    @Override
    public int getItemCount() {
        return artistList.size();
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
