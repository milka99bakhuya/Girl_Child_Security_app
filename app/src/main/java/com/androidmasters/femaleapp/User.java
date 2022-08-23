package com.androidmasters.femaleapp;

public class User {
    private String email_address,phone_number,full_name;

    public User() {
    }

    public User(String email_address, String phone_number, String full_name) {
        this.email_address = email_address;
        this.phone_number = phone_number;
        this.full_name = full_name;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }
}
