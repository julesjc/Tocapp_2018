package com.jules.tocapp;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.location.places.Place;

import java.util.List;
import java.util.Locale;

public class EventActivity extends AppCompatActivity {

    TextView eventName;
    TextView eventDesc;
    TextView location;
    TextView invitedView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        eventName = (TextView) findViewById(R.id.eventname);
        eventDesc = (TextView) findViewById(R.id.eventdesc);
        location = (TextView) findViewById(R.id.location);
        invitedView = (TextView) findViewById(R.id.invited);

        String nameExtra = getIntent().getStringExtra("name");

        ServerFacade.getEvent(nameExtra,this);

    }

    public void fill(Event e)
    {
        eventName.setText(e.name);
        eventDesc.setText(e.description);
        //location.setText("Où : " + e.location.get());
        String invitedString = "Invités : ";
        for (String s : e.invited
             ) {
            invitedString = invitedString + s + ", ";

        }
        invitedView.setText(invitedString);
        try
        {
            Geocoder geocoder = new Geocoder(this, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(e.latitude, e.longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            location.setText(addresses.get(0).getAddressLine(0) + " " + addresses.get(0).getLocality());
        }catch (Exception ex)
        {
            //todo
        }
        getSupportActionBar().setTitle(e.name);

    }

}
