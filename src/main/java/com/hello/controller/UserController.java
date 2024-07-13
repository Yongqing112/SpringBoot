package com.hello.controller;

import com.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/name")
    public String getName(){
        return "hello, World!";
    }

    @GetMapping("/queryUserName/{id}")
    public String queryUserName(@PathVariable("id") String id){
        return userService.queryUserName(id);
    }
}
