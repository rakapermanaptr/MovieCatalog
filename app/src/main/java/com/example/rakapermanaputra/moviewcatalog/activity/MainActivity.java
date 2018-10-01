package com.example.rakapermanaputra.moviewcatalog.activity;

import android.app.AlarmManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.rakapermanaputra.moviewcatalog.R;
import com.example.rakapermanaputra.moviewcatalog.fragment.FavoriteFragment;
import com.example.rakapermanaputra.moviewcatalog.fragment.HomeFragment;
import com.example.rakapermanaputra.moviewcatalog.fragment.NowPlayingFragment;
import com.example.rakapermanaputra.moviewcatalog.fragment.PopularFragment;
import com.example.rakapermanaputra.moviewcatalog.fragment.SearchFragment;
import com.example.rakapermanaputra.moviewcatalog.fragment.UpcomingFragment;
import com.example.rakapermanaputra.moviewcatalog.model.Result;
import com.example.rakapermanaputra.moviewcatalog.reminder.DailyReminder;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String IMG_URL = "https://scontent-sin6-1.xx.fbcdn.net/v/t1.0-9/33044200_1982103725157949_1677686480052420608_n.jpg?_nc_cat=0&oh=73c64ea295ac327cd3aa3f30d2a6bc9c&oe=5BCE341B";

    private CircleImageView profileImageView;
    private String mySearchData;
    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorAccent));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        profileImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);
        Glide.with(MainActivity.this)
                .load(IMG_URL)
                .into(profileImageView);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            Fragment currentFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, currentFragment)
                    .commit();

        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.search));
        searchView.setQueryHint(getResources().getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
                Fragment currentFragment = new SearchFragment();
                mySearchData = query;
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.content_main, currentFragment)
                        .commit();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }

        if (id == R.id.action_scheduler) {
            Toast.makeText(this, "tes scheduler", Toast.LENGTH_SHORT).show();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        String actionBarTitle = null;

        if (id == R.id.nav_home) {
            // Handle the camera action
            Toast.makeText(this, R.string.home, Toast.LENGTH_SHORT).show();
            fragment = new HomeFragment();
            actionBarTitle = getString(R.string.movie_catalog);
        } else if (id == R.id.nav_popular) {
            Toast.makeText(this, R.string.popular, Toast.LENGTH_SHORT).show();
            fragment = new PopularFragment();
            actionBarTitle = getString(R.string.popular);
        } else if (id == R.id.nav_now_playing) {
            Toast.makeText(this, R.string.now_playing, Toast.LENGTH_SHORT).show();
            fragment = new NowPlayingFragment();
            actionBarTitle = getString(R.string.now_playing);
        } else if (id == R.id.nav_upcoming) {
            Toast.makeText(this, R.string.upcoming, Toast.LENGTH_SHORT).show();
            fragment = new UpcomingFragment();
            actionBarTitle = getString(R.string.upcoming);
        } else if (id == R.id.nav_favorite) {
            Toast.makeText(this, R.string.favorite, Toast.LENGTH_SHORT).show();
            fragment = new FavoriteFragment();
            actionBarTitle = getString(R.string.favorite);
        } else if (id == R.id.nav_search) {
            Toast.makeText(this, R.string.search, Toast.LENGTH_SHORT).show();
            fragment = new SearchFragment();
            actionBarTitle = getString(R.string.search_hint);
        } else if (id == R.id.nav_share) {
            Toast.makeText(this, R.string.share, Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_setting) {
            Toast.makeText(this, "Setting", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(this, SettingActivity.class));
            actionBarTitle = "Setting Notification";
        }

        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.content_main, fragment)
                    .addToBackStack(null)
                    .commit();
        }

        getSupportActionBar().setTitle(actionBarTitle);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public String dataSearch(){
        return mySearchData;
    }
}
