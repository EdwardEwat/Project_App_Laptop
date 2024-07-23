package com.example.project_app_laptop.Admin.Model;

public class Customer {
    private int id;
    private String name;
    private String username;

    public Customer(int id, String name, String username) {
        this.id = id;
        this.name = name;
        this.username = username;
    }

    // Getters and setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}