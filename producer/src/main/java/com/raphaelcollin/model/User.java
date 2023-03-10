package com.raphaelcollin.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    Integer id;
    String name;
    String email;
}
