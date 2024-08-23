package com.hello.controller;

import com.hello.entity.Printer;
import com.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("hpPrinter")
    private Printer printer;

    @RequestMapping("/name")
    public String getName(){
        return "hello, World! " + printer.print();
    }

    @GetMapping("/name/{message}")
    public String getNameWithMessage(@PathVariable("message") String message){
        return "hello, World! " + printer.print(message);
    }

    @GetMapping("/queryUserName/{id}")
    public String queryUserName(@PathVariable("id") String id){
        return userService.queryUserName(id);
    }

    @RequestMapping("/product")
    public String product() {
        return "第一個是蘋果、第二個是橘子";
    }
}
