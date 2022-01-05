package com.example.friendsapp;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class UpdateActivity extends AppCompatActivity {

    DatabaseManager dbManager;

    public void onCreate( Bundle savedInstanceState ) {
        super.onCreate( savedInstanceState );

        // instance of DB Manager
        dbManager = new DatabaseManager(this);
        // update screen
        updateView();
    }

    // Build a View dynamically with all the candies
    @SuppressWarnings("deprecation")
    public void updateView( ) {
        // Get all friends form the db table
        ArrayList<Friend> friends = dbManager.selectAll();

        if( friends.size() > 0 ) {
            // create ScrollView and GridLayout
            ScrollView scrollView = new ScrollView( this );
            // Use gridLayout
            GridLayout grid = new GridLayout(this);
            // set columns and rows
            grid.setRowCount(friends.size());
            grid.setColumnCount(5);

            // create arrays of components
            TextView[] ids = new TextView[friends.size( )];
            EditText[][] namesAndEmails = new EditText[friends.size( )][3];
            Button[] buttons = new Button[friends.size( )];
            ButtonHandler bh = new ButtonHandler( );

            // retrieve width of screen
            Point size = new Point();
            getWindowManager().getDefaultDisplay().getSize(size);
            int width = size.x;


            int i = 0;

            // for each loop to retrieve each friend object
            for ( Friend friend: friends ) {

                // create the TextView for the friend's id
                ids[i] = new TextView( this );
                ids[i].setGravity( Gravity.CENTER );
                // set id here
                ids[i].setText(String.format("%d", i+1));

                // create the four EditTexts for the friends's first name last name and email
                // create EditText for first name last name and email
                namesAndEmails[i][0] = new EditText(this);
                namesAndEmails[i][1] = new EditText(this);
                namesAndEmails[i][2] = new EditText(this);
                // get friend first name and last name
                namesAndEmails[i][0].setText( friend.getFname() );
                namesAndEmails[i][1].setText( friend.getLname() );
                // get friend email
                namesAndEmails[i][2].setText( friend.getEmail() );

                namesAndEmails[i][0].setId( 10 * friend.getId( ) );
                namesAndEmails[i][1].setId( 10 * friend.getId( ) + 1 );
                namesAndEmails[i][2].setId( 10 * friend.getId( ) + 2 );

                // create the button
                buttons[i] = new Button( this );
                buttons[i].setText( "Update" );
                buttons[i].setId( friend.getId( ) );

                // set up event handling
                buttons[i].setOnClickListener( bh );

                // add the elements to grid
                grid.addView( ids[i], width / 10, ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( namesAndEmails[i][0], ( int ) ( width * .2 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( namesAndEmails[i][1], (int) ( width * .2 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( namesAndEmails[i][2], (int) ( width * .2 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );
                grid.addView( buttons[i], ( int ) ( width * .2 ),
                        ViewGroup.LayoutParams.WRAP_CONTENT );

                i++;
            }

            // create a back button
            Button backButton = new Button(this);
            backButton.setText("Go Back");
            backButton.setBackgroundColor(0xFF6200EE);
            backButton.setTextColor(Color.WHITE);


            TextView emptyText = new TextView(this);
            grid.addView(emptyText,( int ) ( width / 10 ), ViewGroup.LayoutParams.WRAP_CONTENT );
            grid.addView( backButton, ( int ) ( width * .15 ),ViewGroup.LayoutParams.WRAP_CONTENT );

            backButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // finish the current update activity
                    UpdateActivity.this.finish();
                }
            });

            // Add views
            scrollView.addView(grid);
            setContentView(scrollView);
        }
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick( View v ) {
            // retrieve name and email of the friend
            int friendId = v.getId();
            EditText fnameET = findViewById(10 * friendId);
            EditText lnameET = findViewById(10 * friendId + 1);
            EditText emailET = findViewById(10 * friendId + 2);

            String fname = fnameET.getText().toString();
            String lname = lnameET.getText().toString();
            String email = emailET.getText().toString();

            // update friend in database
            dbManager.updateById(friendId, fname, lname, email);

            Toast.makeText(UpdateActivity.this, String.format("%s %s has been updated", fname, lname), Toast.LENGTH_LONG).show();
            // update screen
            updateView();
        }
    }
}