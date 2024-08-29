package com.hello.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

//步驟一，把class變成public，並且創建相關的註解
@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {

    //步驟二，注入Bean
    @Autowired
    private MockMvc mockMvc;

    //步驟三，建立測試單元
    //在建立MockMvc的時候，主要會分成三個部分
    @Test
    public void create() throws Exception{
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "name": "0829"
                        }
                        """);

        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.id",any(Integer.class)))
                .andExpect(jsonPath("$.name",equalTo("0829")))
                .andReturn();

        //使用mvcResult取得responseBody資訊
        String body = mvcResult.getResponse().getContentAsString();
        //顯示
        System.out.println("返回的responseBody為："+ body);

    }

    @Test
    //後面throws Exception是因為下面的perform會去噴出一個Exception，所以才需要在方法上thorw出來
    public void read() throws Exception{
        /*
        第一個部分就是會去創建一個requestBuilder，
        requestBuilder他會決定要發起的http requst，url路徑
        甚至header，他其實就是一個APItester的概念
         */

        //RequestBuilder也使用了Builder設計模式
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/students/1");
                //等同於 .get("/students/{studentId}",3);
//                .header("headerName","headerValue")
//                .queryParam("graduate","true");

        /*
        第二個部分就是mockMvc.perform()程式，
        他的用途就是在執行上面的requestBuilder
        這個方法就等同於在APItester按下send
         */
        MvcResult mvcResult = mockMvc.perform(requestBuilder)
                /*
                第三個部分就是在perform後面的程式，
                andExpect這個方法就是用來驗證結果，
                很像assert的概念，主要有
                andDo()、andExpect、andReturn
                來輸出、驗證、取得結果
                 */
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(jsonPath("$.id",equalTo(1)))
                .andExpect(jsonPath("$.name",equalTo("0829")))
                .andReturn();

        //使用mvcResult取得responseBody資訊
        String body = mvcResult.getResponse().getContentAsString();
        //顯示
        System.out.println("返回的responseBody為："+ body);
    }

}