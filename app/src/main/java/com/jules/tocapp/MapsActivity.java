package com.jules.tocapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, NavigationView.OnNavigationItemSelectedListener,View.OnClickListener {

    private GoogleMap mMap;
    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setTitle("Carte");
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton button = (FloatingActionButton) findViewById(R.id.refresh);
        button.setOnClickListener(this);
        /*if (savedInstanceState == null) {
            showFragment(new FeedFragment());
        }*/
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        mMap = googleMap;
        //DERNIERE POSITION CONNUE
        // TODO : FAIRE FENETRE PERMISSION
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            Location location = getLastKnownLocation();

            LatLng pos = new LatLng(location.getLatitude(), location.getLongitude());
            Marker me = mMap.addMarker(new MarkerOptions().position(pos).title(ServerFacade.user + " (Moi)"));
            me.showInfoWindow();
            mMap.addMarker(new MarkerOptions()
                    .position(pos)
                    .title("Moi")
                    .snippet("Je suis la")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));

            addUsers(mMap, pos);
        }
    }

    private Location getLastKnownLocation() {
        locationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = locationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            Location l = locationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    public void refresh(GoogleMap googleMap)
    {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Criteria criteria = new Criteria();
            LocationManager locationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);

            Location location = getLastKnownLocation();

            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
        }
    }

    public void addUsers(GoogleMap mMap, LatLng userPos)
    {
        float rad = 75;
        for (int i = 0; i < 10; i++) {
            Random rdm = new Random();
            LatLng piPos = new LatLng(userPos.latitude - (rdm.nextFloat()-0.5f)/rad, userPos.longitude + (rdm.nextFloat()-0.5f)/rad);
            List<String> list = new ArrayList<String>();
            list.add("Odin");
            list.add("Isaac");
            list.add("Arnaud");
            list.add("Jean-Hugues");
            list.add("Junior");
            list.add("Christopher");
            mMap.addMarker(new MarkerOptions().position(piPos).title(list.get(rdm.nextInt(list.size()))));
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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        switch (id) {
            case R.id.nav_profil:
                Intent profileActivity = new Intent(this, ProfileActivity.class);
                startActivity(profileActivity);
                break;
            case R.id.nav_amis:
                Intent friendsActivity = new Intent(this, FriendsListActivity.class);
                startActivity(friendsActivity);
                break;
            case R.id.nav_messages:
                Intent messagesActivity = new Intent(this, ConversationsListActivity.class);
                startActivity(messagesActivity);
                break;
            case R.id.nav_events:
                Intent eventsActivity = new Intent(this, EventListActivity.class);
                startActivity(eventsActivity);
                break;
            case R.id.nav_settings:
                Intent settingsActivity = new Intent(this, SettingsActivity.class);
                startActivity(settingsActivity);
                break;
            case R.id.nav_about:
                //showFragment(new GalleryFragment());
                break;
            default:
                return false;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.refresh: {
                refresh(mMap);
                break;
            }
        }
    }
}

