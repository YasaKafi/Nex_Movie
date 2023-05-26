package com.example.nexmovie;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBar extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);




        // Inisialisasi BottomNavigationView
        bottomNavigationView = findViewById(R.id.navBar);

        replaceActivity(MainActivity.class);

        // Set listener untuk item-item menu pada BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {


            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                // Handle aksi yang sesuai dengan item yang dipilih
                switch (item.getItemId()) {
                    case R.id.ic_home:
                        // Aksi untuk item "Main"
                        replaceActivity(MainActivity.class);
                        break;
                    case R.id.ic_search:
                        // Aksi untuk item "Search Page"
                        Intent searchIntent = new Intent(NavigationBar.this, SearchPage.class);
                        startActivity(searchIntent);
                        break;
                    case R.id.ic_watchlist:
                        // Aksi untuk item "Wishlist"
                        Intent wishlistIntent = new Intent(NavigationBar.this, WatchListPage.class);
                        startActivity(wishlistIntent);
                        break;
                    case R.id.ic_profile:
                        // Aksi untuk item "Profile Page"
                        Intent profileIntent = new Intent(NavigationBar.this, ProfilePage.class);
                        startActivity(profileIntent);
                        break;
                }
                return true;
            }
        });
    }
    private void replaceActivity(Class<? extends Activity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
    }
}