package com.hello.controller;

import com.hello.entity.TodoList.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController {

    @GetMapping("/hello")
    public String hello(Model model){
        model.addAttribute("hello", "Hello World!!!"); // （變數名稱，變數值)
        return "hello";
    }

    @GetMapping("/person")
    public String showGender(Model model){
        model.addAttribute("gender", "female");
        return "person";
    }

    @GetMapping("/list")
    public String listNumber(Model model){
        List<String> list = new ArrayList<>();

        for(int i = 0; i <= 10; i++){
            list.add("This is ArrayList " + i);
        }
        model.addAttribute("list", list);
        return "list";
    }

    @GetMapping("/form")
    public String form(Model model){
        Person person = new Person();
        model.addAttribute("person", person);
        return "form";
    }

    @PostMapping("/add")
    public String add(@ModelAttribute Person person, Model model){
        model.addAttribute("person", person);
        return "add";
    }
}
