package com.example.jol.musicapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onResume() {
        super.onResume();
        startSavedAlbumFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_search:
                startSearchActivity();
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        AlbumOverviewFragment albumOverviewFragment = (AlbumOverviewFragment) getSupportFragmentManager().findFragmentByTag("saved_album_overview");
        if (albumOverviewFragment != null && albumOverviewFragment.isVisible()) {
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().setTitle("Home");
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
    }
    private void startSearchActivity() {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    private void startSavedAlbumFragment() {
        FragmentManager fm = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        SavedAlbumListFragment savedAlbumListFragment = new SavedAlbumListFragment();
        ft.replace(R.id.fragment_container_2, savedAlbumListFragment, "saved_albums_fragment");
        ft.commit();
    }
}
