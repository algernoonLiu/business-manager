package com.gary.businessmanager.service.impl;

import com.gary.businessmanager.dao.UserRepository;
import com.gary.businessmanager.dto.RegisterUser;
import com.gary.businessmanager.model.User;
import com.gary.businessmanager.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findOneByEmail(email);
    }

    @Override
    public User saveRegisterUser(RegisterUser registerUser) {
        User user = new User();
        user.setName(registerUser.getName());
        user.setEmail(registerUser.getEmail());
        user.setPassword(registerUser.getPassword());
        user.setIdentity(registerUser.getIdentity());
        return userRepository.save(user);
    }

    @Override
    public User saveOrUpdateUser(User user) {
        return userRepository.save(user);
    }
}
