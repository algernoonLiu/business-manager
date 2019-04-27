package com.gary.businessmanager.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("User")
public class User {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String email;
    private String password;
    private String identity;
    private String token;

}
