package com.raphaelcollin.model;

public class Event {
    private final User user;

    public Event(final User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}
