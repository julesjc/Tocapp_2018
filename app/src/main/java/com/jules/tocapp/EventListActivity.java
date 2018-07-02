package com.jules.tocapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class EventListActivity extends AppCompatActivity {

    ListView eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Evenements");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.add_event);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addEvent();
            }
        });

        eventList = (ListView) findViewById(R.id.eventList);

        eventList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                OpenEvent(eventList.getItemAtPosition(position).toString());
            }
        });
        ServerFacade.getEventsName(this);
    }

    public void addEvent()
    {
        Intent newEventActivity = new Intent(this, NewEventActivity.class);
        startActivity(newEventActivity);
    }

    public void fillList(List l)
    {
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(EventListActivity.this,
                android.R.layout.simple_list_item_1, l);
        eventList.setAdapter(adapter);
    }

    public void OpenEvent(String name)
    {
        Intent intent = new Intent(this, EventActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }


}
