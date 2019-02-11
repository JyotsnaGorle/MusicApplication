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

import java.util.ArrayList;

public class ArtistRecyclerViewAdapter extends RecyclerView.Adapter<ArtistRecyclerViewAdapter.ViewHolder>  {

    ArrayList<LastFMArtist> artistList;
    Context context;

    ArtistRecyclerViewAdapter(Context context, LastFMArtistsResposne artistsResposne){
        this.artistList = artistsResposne.artist;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View listItem= layoutInflater.inflate(R.layout.artist_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        String url = artistList.get(i).image.get(1).text;
        if(viewHolder.imageView != null) {
            new DownloadImageTask(viewHolder.imageView).execute(url);
        }
        viewHolder.textView.setText(artistList.get(i).name);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = ((MainActivity) context).getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                Bundle args = new Bundle();
                args.putParcelable("artist", artistList.get(i));
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
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.artist_image);
            this.textView = (TextView) itemView.findViewById(R.id.artist_name);
        }
    }
}
