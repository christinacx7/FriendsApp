package com.example.friendsapp;

import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.NumberFormat;
import java.util.ArrayList;

public class FriendsActivity extends AppCompatActivity {

    private DatabaseManager dbManager;
    private ScrollView scrollView;
    private int with;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // content View
        setContentView(R.layout.activity_friends);

        dbManager = new DatabaseManager( this );
        scrollView = ( ScrollView ) findViewById( R.id.scrollView );
        Point size = new Point( );
        getWindowManager().getDefaultDisplay().getSize(size);
        with = size.x / 2;
        // update view
        updateView();
    }

    public void updateView( ) {
        ArrayList<Friend> friends = dbManager.selectAll( );

        if( friends.size( ) > 0 ) {
            // remove subviews inside scrollView if necessary
            scrollView.removeAllViewsInLayout( );
            GridLayout grid = new GridLayout(this);
            grid.setRowCount(friends.size());
            grid.setColumnCount(2);

            // create an array of candy buttons
            FriendButton[] buttons = new FriendButton[friends.size()];
            ButtonHandler bh = new ButtonHandler();

            // fill the grid
            int i = 0; String friendInfo;
            for ( Friend friend : friends ) {
                buttons[i] = new FriendButton(this, friend);
                friendInfo = friend.getFname() + "\n" + friend.getLname() + "\n" + friend.getEmail();
                buttons[i].setText(friendInfo);
                buttons[i].setOnClickListener(bh);

                // add button to grid
                grid.addView(buttons[i], with, GridLayout.LayoutParams.WRAP_CONTENT);
                i++;
            }

            // create a back button
            Button btnBack = new Button(this);
            btnBack.setText("Go Back");
            btnBack.setBackgroundColor(0xFF6200EE);
            btnBack.setTextColor(Color.WHITE);


            TextView emptyText = new TextView(this);
            grid.addView(emptyText,( int ) ( with ), ViewGroup.LayoutParams.WRAP_CONTENT );
            grid.addView( btnBack, ( int ) ( with ), ViewGroup.LayoutParams.WRAP_CONTENT );

            btnBack.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // close the activity
                    FriendsActivity.this.finish();
                }
            });

            scrollView.addView(grid);
        }
    }

    private class ButtonHandler implements View.OnClickListener {
        public void onClick( View v ) {

            Toast.makeText(FriendsActivity.this, "Friend has been clicked!", Toast.LENGTH_LONG).show();
        }
    }
}
