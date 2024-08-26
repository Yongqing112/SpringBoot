package com.hello.dto;

public class StudentRequest implements Request{

    private String id;

    private String user_name;

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public String getUser_name() {
        return user_name;
    }
}
