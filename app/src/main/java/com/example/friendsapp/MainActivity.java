package com.example.friendsapp;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.ui.AppBarConfiguration;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private DatabaseManager dbManager;
    private Button btnFriends;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dbManager = new DatabaseManager(this);

        btnFriends = (Button) findViewById(R.id.button_friends);

        btnFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFriends = new Intent(MainActivity.this, FriendsActivity.class);
                startActivity(intentFriends);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement Add
        if (id == R.id.action_add) {
            Intent addintent = new Intent(this, AddActivity.class);
            this.startActivity(addintent);
            return true;
        }

        //noinspection SimplifiableIfStatement Delete
        if (id == R.id.action_delete) {
            Intent deleteintent = new Intent(this, DeleteActivity.class);
            this.startActivity(deleteintent);
            return true;
        }

        //noinspection SimplifiableIfStatement Update
        if (id == R.id.action_update) {
            Intent updateintent = new Intent(this, UpdateActivity.class);
            this.startActivity(updateintent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void search(View v) {
        // take the text provided in the email editText and assign it to a string
        EditText emailET = findViewById(R.id.emailText);
        String emailString = emailET.getText().toString();


        String friendString = (dbManager.searchByEmail(emailString)).toString();

        friendString = friendString.replace("[", "").replace("]", "");
        String[] friend = friendString.split(" ");

        if(friendString != "") {
            // Set textView fname, lname, and email to the friend being returned in the searchByEmail method
            TextView fname = findViewById(R.id.fname);
            TextView lname = findViewById(R.id.lname);
            TextView email = findViewById(R.id.emailview);

            fname.setText(friend[0]);
            lname.setText(friend[1]);
            email.setText(friend[2]);

            Toast.makeText(this, friend[0] + " " + friend[1] + " is in the Friend table", Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this, emailString + " is NOT in the Friend table", Toast.LENGTH_LONG).show();
        }

        // clear edit text
        emailET.setText("");
    }
}