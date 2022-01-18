package com.fsk.microservice.autoparking.controller;

import com.fsk.microservice.autoparking.entity.User;
import com.fsk.microservice.autoparking.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@Slf4j
public class LoginController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity getJWT(@RequestBody User user){
        log.info("user found is : {}", user.toString());
        return ResponseEntity.accepted().body(userService.logIn(user));
    }
}
