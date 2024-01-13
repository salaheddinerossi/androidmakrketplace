package com.example.marketplace;


import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.os.Bundle;

import com.example.marketplace.fragments.CreateProductFragment;
import com.example.marketplace.fragments.HomeFragment;
import com.example.marketplace.fragments.LogoutFragment;
import com.example.marketplace.fragments.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoggedInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in);

        BottomNavigationView bottomNav = findViewById(R.id.loggedInBottomNavigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Load default fragment (e.g., ViewProfileFragment)
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.loggedInFragmentContainer, new HomeFragment())
                .commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment = null;
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.navigation_logout) {
                    selectedFragment = new LogoutFragment();
                }else if (itemId == R.id.navigation_create_product){
                    selectedFragment = new CreateProductFragment();
                }else if (itemId == R.id.navigation_view_profile){
                    selectedFragment = new ProfileFragment();
                }

                if (selectedFragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.loggedInFragmentContainer,
                            selectedFragment).commit();
                }

                return true;
            };
}
