package com.hello.dao.impl;

import com.hello.dao.Dao;
import com.hello.dto.BookRequest;
import com.hello.dto.Request;
import com.hello.entity.other.Book;
import com.hello.mapper.BookRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BookDao implements Dao<Book> {
    @Override
    public void update(String Id, Request request) {

    }

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Override
    public Book readById(String id) {
        return null;
    }

    @Override
    public Book readById(Integer id) {
        String sql = "SELECT book_id, title, author, image_url, price, published_date, created_date, last_modified_date " +
                "FROM book WHERE book_id = :bookId";
        Map<String, Object> map = new HashMap<>();
        RowMapper<Book> rowMapper = new BookRowMapper();
        map.put("bookId", id);

        List<Book> list = namedParameterJdbcTemplate.query(sql, map, rowMapper);

        if(!list.isEmpty()){
            return list.get(0);
        }
        else{
            return null;
        }
    }

    @Override
    public Integer create(Request request) {

        String sql = "INSERT INTO book(title, author, image_url, price, published_date, created_date, last_modified_date) " +
                "VALUES (:title, :author, :imageUrl, :price, :publishedDate, :createdDate, :lastModifiedDate)";
        BookRequest bookRequest = (BookRequest) request;
        Map<String, Object> map = new HashMap<>();
        map.put("title", bookRequest.getTitle());
        map.put("author", bookRequest.getAuthor());
        map.put("imageUrl", bookRequest.getImageUrl());
        map.put("price", bookRequest.getPrice());
        map.put("publishedDate", bookRequest.getPublishedDate());

        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);

        KeyHolder keyHolder = new GeneratedKeyHolder();

        namedParameterJdbcTemplate.update(sql, new MapSqlParameterSource(map), keyHolder);

        int bookId = keyHolder.getKey().intValue();

        return bookId;
    }

    @Override
    public void update(Integer bookId, Request request) {
        String sql = "UPDATE book SET title = :title, author = :author, image_url = :imageUrl, " +
                "price = :price, published_date = :publishedDate, last_modified_date = :lastModifiedDate" +
                " WHERE book_id = :bookId ";
        BookRequest bookRequest = (BookRequest) request;
        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        map.put("title", bookRequest.getTitle());
        map.put("author", bookRequest.getAuthor());
        map.put("imageUrl", bookRequest.getImageUrl());
        map.put("price", bookRequest.getPrice());
        map.put("publishedDate", bookRequest.getPublishedDate());

        map.put("lastModifiedDate", new Date());

        namedParameterJdbcTemplate.update(sql, map);
    }

    @Override
    public void delete(Integer bookId) {
        String sql = "DELETE FROM book WHERE book_id = :bookId ";

        Map<String, Object> map = new HashMap<>();
        map.put("bookId", bookId);

        namedParameterJdbcTemplate.update(sql, map);
    }
}
