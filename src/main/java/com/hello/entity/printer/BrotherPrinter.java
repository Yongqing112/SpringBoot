package com.hello.entity.printer;

import org.springframework.stereotype.Component;

@Component
public class BrotherPrinter implements Printer{

    @Override
    public String print() {
        return "Brother Printer";
    }

    @Override
    public String print(String message) {
        return "Brother Printer " + message;
    }
}
