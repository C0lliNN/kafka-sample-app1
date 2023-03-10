package com.raphaelcollin;

import com.github.javafaker.Faker;
import com.raphaelcollin.model.Event;
import com.raphaelcollin.model.User;

public class EventGenerator {
    public Event newRandomEvent() {
        Faker faker = new Faker();
        return new Event(
                User.builder()
                        .id(faker.number().numberBetween(1, 100))
                        .name(faker.name().name())
                        .email(faker.internet().emailAddress())
                        .build()
        );
    }
}
