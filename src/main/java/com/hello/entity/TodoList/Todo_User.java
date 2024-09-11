package com.hello.entity.TodoList;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table
@Data
public class Todo_User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column
    public String name;

    @Column(insertable = false, columnDefinition = "int default 1")
    Integer gender = 1;

    @Column
    public String password;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "todoUser", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("todoUser")
    @EqualsAndHashCode.Exclude
    private List<Todo> todos;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setTodos(List<Todo> todos) {
        this.todos = todos;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getGender() {
        return gender;
    }

    public String getPassword() {
        return password;
    }

    public List<Todo> getTodos() {
        return todos;
    }

    @Override
    public String toString() {
        return "TodoUser{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", password='" + password + '\'' +
                '}';
    }
}
