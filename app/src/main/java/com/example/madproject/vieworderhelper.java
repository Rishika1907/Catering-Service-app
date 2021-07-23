package com.example.madproject;

public class vieworderhelper {

    String eventname;
    String email;
    String count;
    String date;
    String location;

    public vieworderhelper(String eventname, String email, String count, String date, String location) {
        this.eventname = eventname;
        this.email = email;
        this.count = count;
        this.date = date;
        this.location = location;
    }

    public String getEventname() {
        return eventname;
    }

    public String getEmail() {
        return email;
    }

    public String getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }
}

