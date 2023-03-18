package com.example.calculatorapp.model;


public class User {
    public static String TABLE_NAME = "users";
    public static String COLUMN_ID = "id";
    public static String COLUMN_NAME = "name";
    public static String COLUMN_EMAIL = "email";

    private String Name;
    private String Email;

    public User(String name, String email) {
        this.Name = name;
        this.Email = email;
    }

    public String getName() {
        return Name;
    }

    public String getEmail() {
        return Email;
    }

}