package com.hello.entity;

import org.springframework.stereotype.Component;

@Component
public class HpPrinter implements Printer{
    @Override
    public String print() {
        return "HP Printer";
    }

    @Override
    public String print(String message) {
        return "HP Printer " + message;
    }
}
