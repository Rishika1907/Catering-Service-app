package com.example.madproject;

public class userorderhelper {

    String eventname;
    //    String email;
    String count;
    String date;
    String location;
    String status;
    String servicename;

    public userorderhelper(String eventname, String count, String date, String location, String status,String servicename) {
        this.eventname = eventname;
//        this.email = email;
        this.count = count;
        this.date = date;
        this.location = location;
        this.status = status;
        this.servicename = servicename;
    }

    public String getEventname() {
        return eventname;
    }

//    public String getEmail() {
//        return email;
//    }

    public String getCount() {
        return count;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public String getStatus() {
        return status;
    }

    public String getServicename() {
        return servicename;
    }
}

