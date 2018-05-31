package com.jules.tocapp;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText inputPsw;
    EditText inputName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button connect = (Button) findViewById(R.id.connect);
        connect.setOnClickListener(this);
        Button register = (Button) findViewById(R.id.register);
        register.setOnClickListener(this);

        inputName = findViewById(R.id.inputName);
        inputPsw = findViewById(R.id.inputPsw);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case  R.id.connect: {
                ServerFacade.connectUser(inputName.getText().toString(),inputPsw.getText().toString(),this);
                break;
            }
            case  R.id.register: {
                Intent registerActivity = new Intent(this, RegisterActivity.class);
                startActivity(registerActivity);
                break;
            }
        }
    }

    public void loginSuccessful()
    {
        Intent mapActivity = new Intent(this, MapsActivity.class);
        startActivity(mapActivity);
    }

    public void loginFail()
    {
        Snackbar.make(findViewById(android.R.id.content)
                , "Nom ou mot de passe incorrect", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
