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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jol.musicapplication.ArtistRecyclerViewAdapter;
import com.example.jol.musicapplication.Models.Album;
import com.example.jol.musicapplication.Models.LastFMArtistsResposne;
import com.example.jol.musicapplication.R;
import com.example.jol.musicapplication.Service.LastFMService;
import com.example.jol.musicapplication.Service.ServiceResponse;
import com.wang.avi.AVLoadingIndicatorView;

import java.io.IOException;
import java.util.ArrayList;

public class SearchFragment extends Fragment {
    EditText inputSearch;
    Button searchButton;
    private RecyclerView mRecyclerView;
    ArrayList<Album> artistList;
    TextView placeholder;

    AVLoadingIndicatorView loadingIndicatorView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,
                container, false);
        inputSearch = view.findViewById(R.id.search_input);
        searchButton = view.findViewById(R.id.search_button);
        placeholder = view.findViewById(R.id.placeholder);
        loadingIndicatorView = view.findViewById(R.id.avi);
        mRecyclerView = view.findViewById(R.id.artist_list);
        mRecyclerView.setHasFixedSize(true);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(v);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (artistList!= null && artistList.size() > 0) {
            populateList(artistList);
        }
    }

    private void performSearch(View v) {
        String searchText = inputSearch.getText().toString();
        if (!"".equals(searchText)) {
            searchArtists(searchText);
        }
    }

    private void searchArtists(String searchText) {
        LastFMService lastFMService = new LastFMService();
        try {
            startLoader(true);
            lastFMService.searchArtists(getActivity(), searchText, new ServiceResponse() {
                @Override
                public void onResponseReceived(LastFMArtistsResposne lastFMArtistsResposne) {
                    if(lastFMArtistsResposne.album.size() > 0){
                        artistList = lastFMArtistsResposne.album;
                        populateList(lastFMArtistsResposne.album);
                    } else {
                        placeholder.setText("No results found");
                    }
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateList(ArrayList<Album> lastFMArtistsResposne) {
        startLoader(false);
        mRecyclerView.setVisibility(View.VISIBLE);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.Adapter mAdapter = new ArtistRecyclerViewAdapter(getActivity(), lastFMArtistsResposne);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void startLoader(boolean state) {
        if(loadingIndicatorView != null) {
            if (state) {
                loadingIndicatorView.show();
            } else {
                loadingIndicatorView.hide();
            }
        }
    }
}
