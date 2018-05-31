package com.jules.tocapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MessagesActivity extends AppCompatActivity {

    ListView messagesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Messages");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Todo : new message", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        messagesList = (ListView) findViewById(R.id.friendsList);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(MessagesActivity.this,
                android.R.layout.simple_list_item_1, ServerFacade.getMessages());
        messagesList.setAdapter(adapter);
    }

}
