package com.jules.tocapp;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import java.util.List;

public class ConversationsListActivity extends AppCompatActivity {

    ListView conversationsList;
    ArrayAdapter<String> adapter;

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
                newMessageModal();
            }
        });
        conversationsList = (ListView) findViewById(R.id.messageslist);

        conversationsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                OpenMessages(conversationsList.getItemAtPosition(position).toString());
            }
        });

        ServerFacade.getConversationsUsers(this);
    }

    public void OpenMessages(String with)
    {
        Intent intent = new Intent(this, WriteMessageActivity.class);
        intent.putExtra("user", with);
        startActivity(intent);
    }
    public void fillList(List l)
    {
        adapter = new ArrayAdapter<String>(ConversationsListActivity.this,
                android.R.layout.simple_list_item_1, l);
        conversationsList.setAdapter(adapter);
    }

    public void newMessageModal()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        builder.setTitle("Nouveau message");

        // Set up the input
        final EditText receiver = new EditText(this);
        receiver.setHint("Destinataire");
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        receiver.setInputType(InputType.TYPE_CLASS_TEXT);
        final EditText messageText = new EditText(this);
        final ConversationsListActivity act = this;
        messageText.setHint("Message");
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        messageText.setInputType(InputType.TYPE_CLASS_TEXT);
        layout.addView(receiver);
        layout.addView(messageText);
        builder.setView(layout);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ServerFacade.sendMessage(receiver.getText().toString(),messageText.getText().toString());

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

}
