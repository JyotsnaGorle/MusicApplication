package com.example.jol.musicapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class SearchFragment extends Fragment {
    TextView inputSearch;
    Button searchButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search,
                container, false);
        inputSearch = view.findViewById(R.id.search_input);
        searchButton = view.findViewById(R.id.search_button);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.artist_list);
        mRecyclerView.setHasFixedSize(true);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(v);
            }
        });
        return view;
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
            lastFMService.searchArtists(getActivity(), "cher", new ServiceResponse() {
                @Override
                public void onResponseReceived(LastFMArtistsResposne lastFMArtistsResposne) {
                    Log.d("", "");
                    populateList(lastFMArtistsResposne);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateList(LastFMArtistsResposne lastFMArtistsResposne) {
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new ArtistRecyclerViewAdapter(getActivity(), lastFMArtistsResposne);
        mRecyclerView.setAdapter(mAdapter);
    }
}
