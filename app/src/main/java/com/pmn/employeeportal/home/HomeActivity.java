package com.pmn.employeeportal.home;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pmn.employeeportal.R;

public class HomeActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        //first fragment to load by default
        loadFragment(new FeedFragment());

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.feed:
                    selectedFragment = new FeedFragment();
                    break;
                case R.id.menu:
                    selectedFragment = new MenuFragment();
                    break;

            }

            loadFragment(selectedFragment);

            return true;
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.container, fragment)
                .commit();
    }
}