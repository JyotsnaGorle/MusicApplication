package com.example.jol.musicapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private SQLiteDatabaseHandler db;
    private RecyclerView savedArtists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");
        savedArtists = findViewById(R.id.saved_albums);
        savedArtists.setHasFixedSize(true);
        db = SQLiteDatabaseHandler.getInstance(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ArrayList<Artist> allMyMusic = db.allMyMusic();
        if (allMyMusic.size() > 0 )
            populateList(allMyMusic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                startSearchActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void startSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void populateList(ArrayList<Artist> savedArtists) {
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        this.savedArtists.setLayoutManager(mLayoutManager);
        this.savedArtists.setAdapter(new SavedArtistViewAdapter(this, savedArtists));
    }
}
