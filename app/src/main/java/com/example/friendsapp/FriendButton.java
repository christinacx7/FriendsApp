package com.example.friendsapp;

import android.content.Context;

public class FriendButton extends androidx.appcompat.widget.AppCompatButton {
    private Friend friend;

    public FriendButton(Context context, Friend newFriend ) {
        super( context );
        friend = newFriend;
    }
}
