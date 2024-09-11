package com.hello.entity.TodoList;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table
@Data
public class Todos_Tag {
    @Id
    @Column(name = "todo_id", nullable = false)
    private Long todo_id;

    @Column
    private Integer tag_id;

}
