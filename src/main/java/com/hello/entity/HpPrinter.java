package com.hello.entity;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HpPrinter implements Printer{

    @Value("${count}")
    private int count;

    @Override
    public String print() {
        System.out.println("I'm now.");
        return "HP Printer";
    }

    @Override
    public String print(String message) {
        count--;
        return "HP Printer " + message + "<br>次數:" + count;
    }
}
