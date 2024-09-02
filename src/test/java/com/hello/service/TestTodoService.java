package com.hello.service;

import com.hello.dao.TodoDao;
import com.hello.entity.TodoList.Todo;
import com.hello.service.impl.TodoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class TestTodoService {
    @Autowired
    TodoService todoService;

    @MockBean
    TodoDao todoDao;

    @Test
    public void testGetTodos(){
        // [Arrange] 預期資料
        List<Todo> expectedTodosList = new ArrayList<>();
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("洗衣服");
        todo.setStatus(1);
        expectedTodosList.add(todo);

        // 定義模擬呼叫todoDao.findAll() 要回傳的預設結果
        Mockito.when(todoDao.findAll()).thenReturn(expectedTodosList);

        // [Act] 操作todoService.getTodos()
        Iterable<Todo> actualTodoList = todoService.getTodos();

        // [Assert] 預期與實際的資料
        assertEquals(expectedTodosList, actualTodoList);
    }

    @Test
    public void testCreateTodo(){
        // [Arrange]
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("寫鐵人賽文章");
        todo.setStatus(1);

        // 定義模擬呼叫todoDao.save(todo) 的回傳結果
        Mockito.when(todoDao.save(todo)).thenReturn(todo);

        // [Act]
        Integer actualId = todoService.createTodo(todo);

        // [Assert]
        assertEquals(1, actualId);
    }

    @Test
    public void testUpdateTodoSuccess(){
        // 準備資料
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("寫鐵人賽文章");
        todo.setStatus(1);
        Optional<Todo> resTodo = Optional.of(todo);

        // 定義模擬呼叫todoDao.save(todo) 的回傳結果
        Mockito.when(todoDao.findById(1)).thenReturn(resTodo);

        // [Arrange] 更改的資料
        todo.setStatus(2);

        // [Act]
        boolean actualUpdateRlt = todoService.updateTodo(1, todo);

        // [Assert]
        assertEquals(true, actualUpdateRlt);

    }

    @Test
    public void testUpdateTodoNotExistId(){
        // 準備更改的資料
        Todo todo = new Todo();
        todo.setStatus(2);
        Optional<Todo> resTodo = Optional.of(todo);

        // 定義模擬呼叫todoDao.save(todo) 的回傳結果
        Mockito.when(todoDao.findById(100)).thenReturn(Optional.empty());

        // [Act]
        boolean actualUpdateRlt = todoService.updateTodo(100, todo);

        // [Assert]
        assertEquals(false, actualUpdateRlt);

    }

    @Test
    public void testUpdateTodoOccurException(){
        // 準備更改的資料
        Todo todo = new Todo();
        todo.setId(1);
        todo.setStatus(1);
        Optional<Todo> resTodo = Optional.of(todo);

        // 定義模擬呼叫todoDao.save(todo) 的回傳結果
        Mockito.when(todoDao.findById(1)).thenReturn(resTodo);
        todo.setStatus(2);

        // 模擬呼叫todoDao.save(todo)時發生NullPointerException例外
        doThrow(NullPointerException.class).when(todoDao).save(todo);

        // [Act]
        boolean actualUpdateRlt = todoService.updateTodo(1, todo);

        // [Assert]
        assertEquals(false, actualUpdateRlt);

    }

    @Test
    public void testDeleteTodoSuccess(){
        //準備刪除的資料
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("鐵人賽文章");
        todo.setStatus(2);
        Optional<Todo> resTodo = Optional.of(todo);

        // 模擬呼叫todoDao.findById(id)，模擬資料庫有id=1的資料
        Mockito.when(todoDao.findById(1)).thenReturn(resTodo);

        // [Act]
        Boolean actualDeleteRlt = todoService.deleteTodo(1);

        // [Assert]
        assertEquals(true, actualDeleteRlt);
    }

    @Test
    public void testDeleteTodoNotExistId(){
        //準備刪除的資料
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("鐵人賽文章");
        todo.setStatus(2);
        Optional<Todo> resTodo = Optional.of(todo);

        // 模擬呼叫todoDao.findById(id)，模擬資料庫有id=1的資料
        Mockito.when(todoDao.findById(100)).thenReturn(Optional.empty());

        // [Act]
        Boolean actualDeleteRlt = todoService.deleteTodo(11);

        // [Assert]
        assertEquals(false, actualDeleteRlt);
    }

    @Test
    public void testDeleteTodoOccurException(){
        //準備刪除的資料
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("鐵人賽文章");
        todo.setStatus(2);
        Optional<Todo> resTodo = Optional.of(todo);

        // 模擬呼叫todoDao.findById(id)，模擬資料庫有id=1的資料
        Mockito.when(todoDao.findById(1)).thenReturn(resTodo);

        // 模擬呼叫todoDao.save(todo)時發生NullPointerException例外
        doThrow(NullPointerException.class).when(todoDao).deleteById(1);

        // [Act]
        Boolean actualDeleteRlt = todoService.deleteTodo(1);

        // [Assert]
        assertEquals(false, actualDeleteRlt);
    }
}
