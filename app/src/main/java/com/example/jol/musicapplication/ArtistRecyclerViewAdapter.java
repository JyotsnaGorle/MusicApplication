package com.example.jol.musicapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jol.musicapplication.Models.Album;
import com.example.jol.musicapplication.Models.ImageSize;
import com.example.jol.musicapplication.fragments.AlbumOverviewFragment;

import java.io.InputStream;
import java.util.ArrayList;

public class ArtistRecyclerViewAdapter extends RecyclerView.Adapter<ArtistRecyclerViewAdapter.ViewHolder>  {

    private ArrayList<Album> artistList;
    Context context;

    public ArtistRecyclerViewAdapter(Context context, ArrayList<Album> artistList){
        this.artistList = artistList;
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
        String url = currentArtist.image.get(ImageSize.MEDIUM.value).text;

        if(viewHolder.imageView != null) {
            new DownloadImageAsyncTask(viewHolder.imageView).execute(url);
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

    private class DownloadImageAsyncTask extends AsyncTask<String,Void,Bitmap> {
        ImageView imageView;

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
                result = BitmapFactory.decodeResource(context.getResources(), R.drawable.icons8);
            }
            if(imageView != null) {
                imageView.setImageBitmap(result);
            }
        }
    }
}
