package com.example.imtpmd;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity {

    private BottomNavigationView DashboardNav;
    private FrameLayout DashboardFrame;

    private HomeFragment homeFragment;
    private CalendarFragment calendarFragment;
    private SettingsFragment settingsFragment;

    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        DashboardNav = (BottomNavigationView) findViewById(R.id.dashboard_nav);
        DashboardFrame = (FrameLayout) findViewById(R.id.dashboard_frame);

        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        settingsFragment = new SettingsFragment();

        date = new SimpleDateFormat("EEEE").format(new Date()); // Toevoegen dat de dag met een hoofdletter is

        setFragment(homeFragment);
        setTitle(date);

        DashboardNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.nav_home:
                        setFragment(homeFragment);
                        setTitle(date);
                        return true;

                    case R.id.nav_calendar:
                        setFragment(calendarFragment);
                        setTitle("Kalender");
                        return true;

                    case R.id.nav_settings:
                        setFragment(settingsFragment);
                        setTitle("Instellingen");
                        return true;

                    default:
                        return false;
                }
            }
        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.dashboard_frame, fragment);
        fragmentTransaction.commit();
    }
}
