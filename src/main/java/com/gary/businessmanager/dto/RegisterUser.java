package com.gary.businessmanager.dto;

import lombok.Data;

@Data
public class RegisterUser {

    private String name;
    private String email;
    private String password;
    private String identity;

}
