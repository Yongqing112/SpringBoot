package com.hello.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.entity.TodoList.Todo;
import com.hello.service.impl.TodoService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestTodoController {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    TodoService todoService;

    @Test
    public void testCreateTodo() throws Exception{
        //設定資料
        Todo todo = new Todo();
        todo.setTask("洗衣服");
        todo.setId(1);
        todo.setStatus(1);

        JSONObject todoObject = new JSONObject();
        todoObject.put("id", 1);
        todoObject.put("task", "洗衣服");

        // 模擬todoService.createTodo(todo) 回傳 id 1
        Mockito.when(todoService.createTodo(any())).thenReturn(todo.getId());

        // 模擬呼叫[POST] /api/todos
        String actual = mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                .accept(MediaType.APPLICATION_JSON) //response 設定型別
                .contentType(MediaType.APPLICATION_JSON) // request 設定型別
                .content(String.valueOf(todoObject))) // body 內容
                .andExpect(status().isCreated()) // 預期回應的status code 為 201(Created)
                .andReturn().getResponse().getContentAsString();

        assertEquals(1, Integer.parseInt(actual));
    }
    @Test
    public void testGetTodos() throws Exception {
        // 設定資料
        List<Todo> expectedList = new ArrayList();
        Todo todo = new Todo();
        todo.setTask("洗衣服");
        todo.setId(1);
        expectedList.add(todo);

        // 模擬todoService.getTodos() 回傳 expectedList
        Mockito.when(todoService.getTodos()).thenReturn(expectedList);

        // 模擬呼叫[GET] /api/todos
        String returnString = mockMvc.perform(MockMvcRequestBuilders.get("/api/todos")
                        .accept(MediaType.APPLICATION_JSON ))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(Charset.defaultCharset());

        Iterable<Todo> actualList = objectMapper.readValue(returnString, new TypeReference<Iterable<Todo>>() {
        });

        // 判定回傳的body是否跟預期的一樣
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testDeleteTodo() throws Exception{
        // 模擬todoService.deleteTodo(1) 成功回傳true
        Mockito.when(todoService.deleteTodo(1)).thenReturn(true);

        // 模擬呼叫[DELETE] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/1")
                .accept(MediaType.APPLICATION_JSON_UTF8) //response 設定型別
                .contentType(MediaType.APPLICATION_JSON)) // request 設定型別
                .andExpect(status().isNoContent()); // 預期回應的status code 應為 204(No Content)
    }

    @Test
    public void testDeleteTodoIdNotExist() throws Exception{
        //
        Mockito.when(todoService.deleteTodo(100)).thenReturn(false);

        //
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/100")
                .accept(MediaType.APPLICATION_JSON_UTF8) //response 設定型別
                .contentType(MediaType.APPLICATION_JSON)) // request 設定型別
                .andExpect(status().isBadRequest()); // 預期回應的status code 為 400(Bad Request)
    }
}
