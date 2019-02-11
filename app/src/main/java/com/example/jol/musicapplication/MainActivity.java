package com.example.jol.musicapplication;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
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
            case R.id.search_option:
                startSearchFragment();
                break;
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
        if (searchFragment != null && searchFragment.isVisible()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                this.finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
        if (albumOverviewFragment != null && albumOverviewFragment.isVisible()) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                this.finish();
            } else {
                getSupportFragmentManager().popBackStack();
            }
        }
    }

    private void startSearchFragment() {
        FragmentManager fm = getSupportFragmentManager();

        android.support.v4.app.FragmentTransaction ft = fm.beginTransaction();
        SearchFragment searchFragment = new SearchFragment();
        ft.add(R.id.fragment_container, searchFragment, "search_fragment");
        ft.addToBackStack("search_fragment");
        ft.commit();
    }
}
