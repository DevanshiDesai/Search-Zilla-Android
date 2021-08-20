package com.example.eventsearch;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;


public class MainActivity extends AppCompatActivity implements LocationListener{

    TabLayout tabLayout;
    ViewPager2 pager2;
    FragmentAdapter adapter;
    public static final String favPref = "favPref";
    public static SharedPreferences sharedPreference=null;
    public static SharedPreferences.Editor editor;
    static String location;
    LocationManager locationManager;
    public static String clat;
    public static String clong;


    @Override
    public void onLocationChanged(@NonNull Location location) {
        String loc = Double.toString(location.getLatitude()) + "," + Double.toString(location.getLongitude());
        this.setLocation(loc);
        clat = Double.toString(location.getLatitude());
        clong = Double.toString(location.getLongitude());
    }


    public void setLocation(String l){
        this.location = l;
    }

    public String getLocation(){
        return this.location;
    }


    @SuppressLint("MissingPermission")
    private void getLocationLatLng() {
        try {
            locationManager = (LocationManager) getApplicationContext().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,5, MainActivity.this);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedPreference=this.getSharedPreferences(favPref, Context.MODE_PRIVATE);
        editor=sharedPreference.edit();

        TextView t=(TextView) findViewById(R.id.eventheader);
        t.setText("Event Search");
        ToggleButton i1= (ToggleButton) findViewById(R.id.iconfav);
        i1.setVisibility(View.GONE);
        ImageView i2= (ImageView) findViewById(R.id.twitter);
        i2.setVisibility(View.GONE);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        pager2 = (ViewPager2) findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        pager2.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab().setText("SEARCH"));
        tabLayout.addTab(tabLayout.newTab().setText("FAVORITES"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                pager2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                // called when tab unselected
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                // called when a tab is reselected
            }
        });

        //get permission for location
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION
            },100);
        }
        getLocationLatLng();

        pager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.selectTab(tabLayout.getTabAt(position));
            }
        });


    }


}