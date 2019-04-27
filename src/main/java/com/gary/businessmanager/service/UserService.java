package com.gary.businessmanager.service;

import com.gary.businessmanager.dto.RegisterUser;
import com.gary.businessmanager.model.User;

public interface UserService {

    User findUserByEmail(String email);

    User saveRegisterUser(RegisterUser user);

    User saveOrUpdateUser(User userInDB);
}
