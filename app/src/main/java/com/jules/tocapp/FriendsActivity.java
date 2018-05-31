package com.jules.tocapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class FriendsActivity extends AppCompatActivity {

    ListView friendsList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Amis");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_friend);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "TODO : Ajout d'amis", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ServerFacade.getFriends(this);
    }

    public void fillList(List l)
    {
        friendsList = (ListView) findViewById(R.id.friendsList);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(FriendsActivity.this,
                android.R.layout.simple_list_item_1, l);
        friendsList.setAdapter(adapter);
    }

}
