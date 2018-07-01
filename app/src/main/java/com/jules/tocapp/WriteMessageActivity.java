package com.jules.tocapp;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class WriteMessageActivity extends AppCompatActivity {

    List<String> listItems = new ArrayList<>();
    ArrayAdapter<String> adapter;

    ListView messagesList;
    EditText messagetext;
    Button send;

    String with;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_message);

        with = getIntent().getStringExtra("user");
        messagetext = (EditText) findViewById(R.id.messagetext);

        getSupportActionBar().setTitle("Conversation avec " + with);

        send = (Button) findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendAndAddToList(view);
            }
        });

        messagesList = (ListView) findViewById(R.id.messageslist);

        ServerFacade.getMessages(with,this);
    }

    public void SendAndAddToList(View v)
    {
        String text = messagetext.getText().toString();
        listItems.add(text);
        adapter.notifyDataSetChanged();
        ServerFacade.sendMessage(with,text);
    }

    public void fillList(List l) {
        
        adapter = new ArrayAdapter<String>(WriteMessageActivity.this,
                android.R.layout.simple_list_item_1, l);
        messagesList.setAdapter(adapter);

    }
}