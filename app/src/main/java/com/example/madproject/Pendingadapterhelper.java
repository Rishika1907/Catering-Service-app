package com.example.madproject;

public class Pendingadapterhelper {

    String email,key,location;

    public Pendingadapterhelper(String email, String key,String location) {
        this.email = email;
        this.key = key;
        this.location=location;
    }

    public String getLocation() {
        return location;
    }

    public String getEmail() {
        return email;
    }

    public String getKey() {
        return key;
    }
}

