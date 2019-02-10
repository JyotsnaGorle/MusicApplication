package com.example.jol.musicapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;

public class SearchActivity extends AppCompatActivity {

    TextView inputSearch;
    Button searchButton;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.child_toolbar);
        setSupportActionBar(myChildToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        inputSearch = findViewById(R.id.search_input);
        searchButton = findViewById(R.id.search_button);
        mRecyclerView = (RecyclerView) findViewById(R.id.artist_list);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);


        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(v);
            }
        });
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
            lastFMService.searchArtists(this, "cher", new ServiceResponse() {
                @Override
                public void onResponseReceived(LastFMArtistsResposne lastFMArtistsResposne) {
                    Log.d("","");
                    populateList(lastFMArtistsResposne);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void populateList(LastFMArtistsResposne lastFMArtistsResposne) {
        mAdapter = new ArtistRecyclerViewAdapter(this, lastFMArtistsResposne);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
