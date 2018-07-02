package com.jules.tocapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class NewEventActivity extends AppCompatActivity {

    EditText eventName;
    EditText eventDesc;
    TextView location;
    TextView invitedView;

    Button chooseLoc;
    Button invite;
    Button submit;

    List<String> invited;
    double latitude;
    double longitude;

    int PLACE_PICKER_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_event);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Nouvel Evenenement");

        eventName = (EditText) findViewById(R.id.eventname);
        eventDesc = (EditText) findViewById(R.id.eventdesc);
        location = (TextView) findViewById(R.id.location);
        invitedView = (TextView) findViewById(R.id.invited);

        chooseLoc = (Button) findViewById(R.id.chooseloc);
        chooseLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickLocation();
            }
        });
        invite = (Button) findViewById(R.id.invite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openInviteModal();
            }
        });

        submit = (Button) findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submit();
            }
        });

        invited = new ArrayList<>();
        invited.add(ServerFacade.user);

    }

    public void submit()
    {
        if (!eventName.getText().toString().isEmpty() & !eventDesc.getText().toString().isEmpty() &
                latitude != 0 & longitude!= 0 & !invited.isEmpty())
        {
            Event event = new Event(eventName.getText().toString(),eventDesc.getText().toString(),latitude,longitude,invited);
            ServerFacade.submitEvent(event, this);
        }
        else
        {
            Snackbar.make(findViewById(android.R.id.content), "Erreur saisie", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }

    public void pickLocation()
    {
        try {
            PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
            startActivityForResult(builder.build(this), PLACE_PICKER_REQUEST);
        } catch (Exception e) {
            //todo
        }
    }

    public void openInviteModal()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Inviter");
        final NewEventActivity act = this;
        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ServerFacade.eventFriendExists(input.getText().toString(),act);
            }
        });
        builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    public void eventExists()
    {
        Snackbar.make(findViewById(android.R.id.content), "Cet évènement existe déjà", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }


    public void friendExists(String name)
    {
        invited.add(name);
        invitedView.setText(invitedView.getText() + " " + name);
        Snackbar.make(findViewById(android.R.id.content), name + " Ajouté", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void friendnotExists()
    {

        Snackbar.make(findViewById(android.R.id.content),"Ami inconnu", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(this, data);
                latitude = place.getLatLng().latitude;
                longitude = place.getLatLng().longitude;
                location.setText("Où : " + place.getAddress());

            }
        }
    }

    public void success()
    {
        finish();
    }


}
