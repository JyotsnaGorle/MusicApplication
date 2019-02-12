package com.example.jol.musicapplication;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        Toolbar mToolbar = findViewById(R.id.search_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Search");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        startSearchFragment();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        SearchFragment searchFragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("search_fragment");
        AlbumOverviewFragment albumOverviewFragment = (AlbumOverviewFragment) getSupportFragmentManager().findFragmentByTag("album_overview");
        if (searchFragment != null && searchFragment.isVisible() || getSupportFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        }
        if (albumOverviewFragment != null && albumOverviewFragment.isVisible()) {
            getSupportFragmentManager().popBackStack();
            getSupportActionBar().setTitle("Search");
        }
    }

    private void startSearchFragment() {
        FragmentManager fm = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        ft.replace(R.id.fragment_container, searchFragment, "search_fragment");
        ft.addToBackStack("search_fragment");
        ft.commit();
    }
}
