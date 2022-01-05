package com.example.friendsapp;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class DatabaseManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "FriendDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_FRIEND = "friendTEST";
    private static final String ID = "id";
    private static final String FNAME = "fname";
    private static final String LNAME = "lname";
    private static final String EMAIL = "email";

    public DatabaseManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( "drop table if exists " + TABLE_FRIEND );
        /*String sqlCreate = "create table " + TABLE_CANDY + " ( " + ID;
        sqlCreate += " integer primary key autoincrement, " + NAME;
        sqlCreate += " text, " + PRICE + " real )";*/

        String sqlCreate = String.format("Create Table %s ( %s Integer Primary Key Autoincrement, %s Text, %s Text, %s Text )",
                TABLE_FRIEND, ID, FNAME, LNAME, EMAIL);

        db.execSQL(sqlCreate);
    }

    public void insertFriend(Friend friend) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlInsert = String.format("Insert Into %s Values (null, '%s', '%s', '%s')",
                TABLE_FRIEND, friend.getFname(), friend.getLname(), friend.getEmail());

        db.execSQL(sqlInsert);
        db.close();
    }

    public ArrayList<Friend> selectAll() {
        //String sqlQuery = "select * from " + TABLE_CANDY;

        String sqlQuery = String.format("Select * From %s", TABLE_FRIEND);
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(sqlQuery, null);

        ArrayList<Friend> friends = new ArrayList<>();

        while (cursor.moveToNext()) {
            Friend currentFriend = new Friend(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
            friends.add(currentFriend);
        }
        db.close();
        return friends;
    }

    public void deleteById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sqlDelete = String.format("Delete From %s Where %s = %d", TABLE_FRIEND, ID, id);

        db.execSQL(sqlDelete);
        db.close();
    }

    public void updateById(int id, String fname, String lname, String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        String sqlUpdate = String.format("Update %s Set %s = '%s', %s = '%s', %s = '%s' Where %s = %d",
                TABLE_FRIEND, FNAME, fname, LNAME, lname, EMAIL, email, ID, id);

        db.execSQL(sqlUpdate);
        db.close();
    }

    public ArrayList<Friend> searchByEmail(String email) {
        String sqlSearch = String.format("Select * From %s Where %s = '%s'", TABLE_FRIEND, EMAIL, email);
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(sqlSearch, null);
        ArrayList<Friend> friends = new ArrayList<>();

        while (cursor.moveToNext()) {
            Friend currentFriend = new Friend(Integer.parseInt(cursor.getString(0)), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3));
            friends.add(currentFriend);
        }

        db.close();
        return friends;
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion ) {
        // Drop old table if it exists
        db.execSQL( "drop table if exists " + TABLE_FRIEND );
        // Re-create tables
        onCreate(db);
    }

}