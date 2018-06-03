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

public class ConversationsActivity extends AppCompatActivity {

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

        ServerFacade.getConversations(this);
    }

    public void fillList(List l)
    {
        messagesList = (ListView) findViewById(R.id.messageslist);

        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(ConversationsActivity.this,
                android.R.layout.simple_list_item_1, l);
        messagesList.setAdapter(adapter);
    }

}
