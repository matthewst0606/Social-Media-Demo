package com.fantastic.model;


public class User {
    private String email, username, password, created_at;
    private int user_id;

    public User(String email, String username, String password) {
        this.user_id = -1;
        this.email = email;
        this.username = username;
        this.password = password;
        this.created_at = null;
    }


    public int getUserID () { return this.user_id; }
    public String getEmail() { return this.email; }
    public String getUsername() { return this.username; }
    public String getPassword() { return this.password; }
    public String getCreatedAt() { return this.created_at; }

    public void setUserID(int id) { this.user_id = id; }
    public void setCreatedAt(String date) { this.created_at = date; }




}
