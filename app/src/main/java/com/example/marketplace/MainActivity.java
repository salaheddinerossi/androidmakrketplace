package com.example.marketplace;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import com.example.marketplace.fragments.HomeFragment;
import com.example.marketplace.fragments.LoginFragment;
import com.example.marketplace.fragments.RegisterFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Check if the token exists
        if (!getToken().isEmpty()) {
            // Token exists, redirect to LoggedInActivity
            Intent intent = new Intent(MainActivity.this, LoggedInActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);

        // Set the initial fragment if no token is found
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            bottomNav.setSelectedItemId(R.id.navigation_home); // Optional: Mark the Home item as selected
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            item -> {
                Fragment selectedFragment;
                int itemId = item.getItemId();

                if (itemId == R.id.navigation_home) {
                    selectedFragment = new HomeFragment();
                } else if (itemId == R.id.navigation_login) {
                    selectedFragment = new LoginFragment();
                } else if (itemId == R.id.navigation_register) {
                    selectedFragment = new RegisterFragment();
                } else {
                    return false;
                }

                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                return true;
            };

    public String getToken() {
        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        return sh.getString("jwtToken", "");
    }
}
