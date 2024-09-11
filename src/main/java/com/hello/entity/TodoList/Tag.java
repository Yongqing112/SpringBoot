package com.hello.entity.TodoList;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    @Getter
    @Setter
    private String tag;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "tags")
    List<Todo> todos;


}
