package com.jules.tocapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Inscription");

        Button sub = (Button) findViewById(R.id.submit);
        sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerNewUserToDB(view);
            }
        });
    }

    void registerNewUserToDB(View v) {

        TextView mail = findViewById(R.id.mail);
        TextView name = findViewById(R.id.name);
        TextView password = findViewById(R.id.password);
        TextView password2 = findViewById(R.id.password2);

        if (password.getText().toString().equals(password2.getText().toString()) &&
                !mail.getText().toString().isEmpty() && !name.getText().toString().isEmpty()
                && !password.getText().toString().isEmpty() && !password2.getText().toString().isEmpty()) {

            ServerFacade.registerUser(name.getText().toString(),mail.getText().toString(),password.getText().toString());

            finish();
        }

        else
        {
            Snackbar.make(v, "Erreur saisie", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        }

    }


}
