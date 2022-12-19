package com.raphaelcollin.model;

public class User {
    private final int id;
    private final String name;
    private final String email;

    public User(final int id, final String name, final String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}
