package com.example.nexmovie;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.nexmovie.Fragment.HomeFragment;
import com.example.nexmovie.Fragment.ProfileFragment;
import com.example.nexmovie.Fragment.SearchFragment;
import com.example.nexmovie.Fragment.WatchListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationBar extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private HomeFragment homeFragment;
    private SearchFragment filmFragment;
    private WatchListFragment watchlistFragment;
    private ProfileFragment profileFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_bar);

        bottomNavigationView = findViewById(R.id.navBar);
        homeFragment = new HomeFragment();
        filmFragment = new SearchFragment();
        watchlistFragment = new WatchListFragment();
        profileFragment = new ProfileFragment();

        MenuItem homeMenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_home);
        homeMenuItem .setIcon(R.drawable.ic_home_active); // Ikon  aktif
        homeMenuItem.setChecked(true);

        MenuItem searchMenuItem  = bottomNavigationView.getMenu().findItem(R.id.ic_search);
        searchMenuItem.setIcon(R.drawable.ic_search_inactive); // Ikon tidak aktif

        MenuItem watchListMenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_watchlist);
        watchListMenuItem.setIcon(R.drawable.ic_bookmark_inactive); // Ikon tidak aktif

        MenuItem profileMenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_profile);
        profileMenuItem.setIcon(R.drawable.ic_profile_inactive); // Ikon tidak aktif

        replaceFragment(homeFragment);

        BottomNavigationView bottomNavigationView = findViewById(R.id.navBar);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.ic_home:
                    item.setIcon(R.drawable.ic_home_active);
                    MenuItem searchsMenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_search);
                    MenuItem watchMenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_watchlist);
                    MenuItem profilesMenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_profile);
                    searchsMenuItem.setIcon(R.drawable.ic_profile_inactive);
                    watchMenuItem.setIcon(R.drawable.ic_bookmark_inactive);
                    profilesMenuItem.setIcon(R.drawable.ic_profile_inactive);
                    selectedFragment = homeFragment;
                    break;
                case R.id.ic_search:
                    item.setIcon(R.drawable.ic_search_active);
                    MenuItem home2MenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_home);
                    MenuItem watch2MenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_watchlist);
                    MenuItem profile2MenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_profile);
                    home2MenuItem.setIcon(R.drawable.ic_home_inactive);
                    watch2MenuItem.setIcon(R.drawable.ic_bookmark_inactive);
                    profile2MenuItem.setIcon(R.drawable.ic_profile_inactive);
                    selectedFragment = filmFragment;
                    break;
                case R.id.ic_watchlist:
                    item.setIcon(R.drawable.ic_bookmark_active);
                    MenuItem home3MenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_home);
                    MenuItem search3MenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_search);
                    MenuItem profile3MenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_profile);
                    home3MenuItem.setIcon(R.drawable.ic_home_inactive);
                    search3MenuItem.setIcon(R.drawable.ic_profile_inactive);
                    profile3MenuItem.setIcon(R.drawable.ic_profile_inactive);
                    selectedFragment = watchlistFragment;
                    break;
                case R.id.ic_profile:
                    item.setIcon(R.drawable.ic_profile_active);
                    MenuItem home4MenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_home);
                    MenuItem search4MenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_search);
                    MenuItem watch4MenuItem = bottomNavigationView.getMenu().findItem(R.id.ic_watchlist);
                    home4MenuItem.setIcon(R.drawable.ic_home_inactive);
                    search4MenuItem.setIcon(R.drawable.ic_profile_inactive);
                    watch4MenuItem.setIcon(R.drawable.ic_bookmark_inactive);
                    selectedFragment = profileFragment;
                    break;
            }
            replaceFragment(selectedFragment);
            return true;
        });


    }
    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }
}