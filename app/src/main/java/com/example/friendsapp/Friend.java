package com.example.friendsapp;

public class Friend {
    private int id;
    private String fname;
    private String lname;
    private String email;

    public Friend( int newId, String newFname, String newLname, String newEmail ) {
        setId( newId );
        setFname( newFname );
        setLname( newLname );
        setEmail( newEmail );
    }

    public void setId (int newId) { id = newId;}

    public void setFname (String newFname) { fname = newFname; }

    public void setLname (String newLname) { lname = newLname; }

    public void setEmail (String newEmail) { email = newEmail; }


    public int getId( ) {
        return id;
    }

    public String getFname() { return fname; }

    public String getLname() { return lname; }

    public String getEmail() { return email;}

    public String toString( ) { return (  fname + " " + lname + " " + email ); }
}
