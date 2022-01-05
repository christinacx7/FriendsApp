package com.example.friendsapp;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    private DatabaseManager dbManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        dbManager = new DatabaseManager(this);
    }

    public void add(View v) {
        EditText fnameET = findViewById(R.id.input_fname);
        String fnameString = fnameET.getText().toString();

        EditText lnameET = findViewById(R.id.input_lname);
        String lnameString = lnameET.getText().toString();

        EditText emailET = findViewById(R.id.input_email);
        String emailString = emailET.getText().toString();


        Friend friend = new Friend(0, fnameString, lnameString, emailString);
        dbManager.insertFriend(friend);
        Toast.makeText(this, fnameString + " " + lnameString + " is inserted to Friend table", Toast.LENGTH_LONG).show();

        // clear edit text
        fnameET.setText("");
        lnameET.setText("");
        emailET.setText("");
    }

    public void goback(View v) {
        this.finish();
    }
}

