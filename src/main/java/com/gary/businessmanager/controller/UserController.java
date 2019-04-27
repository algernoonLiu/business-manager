package com.gary.businessmanager.controller;

import com.gary.businessmanager.dto.LoginUser;
import com.gary.businessmanager.dto.RegisterUser;
import com.gary.businessmanager.model.User;
import com.gary.businessmanager.service.UserService;
import com.gary.businessmanager.util.JWTUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public int register(@RequestBody RegisterUser registerUser) {
        User userInDB = userService.findUserByEmail(registerUser.getEmail());
        if (userInDB != null) {
            return 0;
        }
        User user = userService.saveRegisterUser(registerUser);
        log.info("用户注册成功！  requestBody: {}", user);
        return 1;
    }

    @PostMapping("/login")
    public String login(@RequestBody LoginUser loginUser) {
        User userInDB = userService.findUserByEmail(loginUser.getEmail());
        if (userInDB != null && loginUser.getPassword().equals(userInDB.getPassword())) {
            String token = JWTUtil.sign(userInDB.getName(), userInDB.getEmail());
            userInDB.setToken(token);
            userService.saveOrUpdateUser(userInDB);
            return token;
        }
        log.info("用户登陆成功！  requestBody: {}", loginUser);
        return "err";
    }

}
