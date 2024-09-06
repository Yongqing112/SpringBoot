package com.hello.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hello.entity.TodoList.Todo;
import com.hello.service.impl.TodoService;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Sql(scripts = "classpath:test/data.sql") // sql 檔案放置的地方
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // 在類中別的每個測試方法之前
public class TestTodoController {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TodoService todoService;
    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void testGetTodos() throws Exception {
        String strDate = "2024-09-06 10:30:00";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = format.parse(strDate);
        // [Arrange] 預期回傳的值
        List<Todo> expectedList = new ArrayList<>();
        Todo todo = new Todo();
        todo.setId(1);
        todo.setTask("洗衣服");
        todo.setCreateTime(date);
        todo.setUpdateTime(date);
        expectedList.add(todo);

        // [Act] 模擬網路呼叫[GET] /api/todos
        String returnString = mockMvc.perform(MockMvcRequestBuilders.get("/api/todos")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);

        Iterable<Todo> actualList = objectMapper.readValue(returnString, new TypeReference<Iterable<Todo>>() {
        });

        // [Assert] 判定回傳的body是否跟預期的一樣
        assertEquals(expectedList, actualList);
    }

    @Test
    public void testCreateTodos() throws Exception {
        // [Arrange] 預期回傳的值
        JSONObject todoObject = new JSONObject();
        todoObject.put("task", "寫文章");

        // [Act] 模擬網路呼叫[POST] /api/todos
        String actualId = mockMvc.perform(MockMvcRequestBuilders.post("/api/todos")
                        .accept(MediaType.APPLICATION_JSON) //response 設定型別
                        .contentType(MediaType.APPLICATION_JSON) // request 設定型別
                        .content(String.valueOf(todoObject))) // body 內容
                .andExpect(status().isCreated()) // 預期回應的status code 為 201(Created)
                .andReturn().getResponse().getContentAsString();

        // [Assert] 判定回傳的body是否跟預期的一樣
        assertEquals(2,  Integer.parseInt(actualId));
    }

    @Test
    public void testUpdateTodo() throws Exception{
        //
        JSONObject todoObject = new JSONObject();
        todoObject.put("status", 2);

        // [Act] 模擬網路呼叫[PUT] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/1")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON) // request 設定型別
                .content(String.valueOf(todoObject))) // body 內容
                .andExpect(status().isOk()); // [Assert] 預期回應的status code 為 200(OK)
    }

    @Test
    public void testUpdateTodoIdNotExist() throws Exception{
        //
        JSONObject todoObject = new JSONObject();
        todoObject.put("status", 2);

        // [Act] 模擬網路呼叫[PUT] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.put("/api/todos/2")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.valueOf(todoObject)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteTodo() throws Exception{
        // [Act] 模擬網路呼叫[DELETE] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testDeleteTodoIdNotExist() throws Exception{
        // [Act] 模擬網路呼叫[DELETE] /api/todos/{id}
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/todos/2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
