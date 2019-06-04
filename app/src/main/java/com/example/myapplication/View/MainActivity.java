package com.example.myapplication.View;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.myapplication.R;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolBar;
    private ViewPager viewPager;
    FragmentManager fm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fm = getSupportFragmentManager();
        // ViewPager
        viewPager = findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));

        // Bottom Navigation
        final BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setOnNavigationItemSelectedListener(navListener);
        toolBar = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolBar);

        bottomNav.setSelectedItemId(R.id.navigation_home);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float v, int i1) {

            }

            @Override
            public void onPageSelected(int position) {
                /*
                if (prevMenuItem != null) {
                    prevMenuItem.setChecked(false);
                }
                else
                {
                    bottomNav.getMenu().getItem(0).setChecked(false);
                }
                bottomNav.getMenu().getItem(position).setChecked(true);
                prevMenuItem = bottomNav.getMenu().getItem(position);
                */
                switch(position) {
                    case 0:
                        getSupportActionBar().setTitle("Exercises");
                        bottomNav.getMenu().getItem(position).setChecked(true);
                        break;
                    case 1:
                        getSupportActionBar().setTitle("Workouts");
                        bottomNav.getMenu().getItem(position).setChecked(true);
                        break;
                    case 2:
                        getSupportActionBar().setTitle("Progress");
                        bottomNav.getMenu().getItem(position).setChecked(true);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }
    // Bottom Navigation
    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    switch(menuItem.getItemId()) {
                        case R.id.navigation_activity:
                            getSupportActionBar().setTitle("Exercises");
                            viewPager.setCurrentItem(0);
                            return true;
                        case R.id.navigation_home:
                            getSupportActionBar().setTitle("Workouts");
                            viewPager.setCurrentItem(1);
                            return true;
                        case R.id.navigation_progress:

                            viewPager.setCurrentItem(2);
                            getSupportActionBar().setTitle("Progress");
                            return true;
                    }

                    return false;
                }
            };


    // ViewPager
    public static class ViewPagerAdapter extends FragmentPagerAdapter {


        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    //Log.d("NAV BAR", "Activity");
                    return ActivityFragment.newInstance();
                case 1:
                    //Log.d("NAV BAR", "Home");
                    return HomeFragment.newInstance();
                case 2:
                    //Log.d("NAV BAR", "Profile");
                    return ProgressFragment.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // Toolbar menu.
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id) {
            case R.id.toolbar_profile:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
