package com.hello.dao;

import java.util.List;

public interface Dao <T>{
    T getById(String id);
}
