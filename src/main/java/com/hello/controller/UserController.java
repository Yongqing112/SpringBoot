package com.hello.controller;

import com.hello.entity.printer.Printer;
import com.hello.entity.other.Store;
import com.hello.entity.other.Student;
import com.hello.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    @Qualifier("hpPrinter")
    private Printer printer;

    @RequestMapping("/name")
    public String getName(@RequestParam String date){
        return "hello, World! " + printer.print() + " " + date;
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
    public Store product() {
        Store store = new Store();
        List<String> list = new ArrayList<>();
        list.add("蘋果");
        list.add("橘子");
        store.setProductList(list);

        return store;
    }

    @RequestMapping("/user")
    public Student user(){
        Student student = new Student();
        student.setName("Scoot");
        return student;
    }

    @RequestMapping("/userId")
    public Student userId(@RequestBody Student student){
        return student;
    }

    @RequestMapping("/header")
    public String header(@RequestHeader String info){
        return info;
    }
}
