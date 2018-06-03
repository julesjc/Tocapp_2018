package com.jules.tocapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {

    Button edit;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profil");

        edit = findViewById(R.id.edit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickEdit();
            }
        });

        description = findViewById(R.id.description);

        fillName(ServerFacade.user);
        ServerFacade.getDescription(this);

    }

    public void onClickEdit()
    {

        if (edit.getText().toString().equals("Editer")) {
            description.setEnabled(true);
            edit.setText("Ok");
        }
        else
        {
            description.setEnabled(false);
            edit.setText("Editer");
            ServerFacade.setDescription(description.getText().toString());
        }


    }

    public void fillDescription(String newdesc)
    {
        description.setText(newdesc);
    }

    public void fillName(String name)
    {
        TextView nameET = findViewById(R.id.name);
        nameET.setText(name);
    }

}
