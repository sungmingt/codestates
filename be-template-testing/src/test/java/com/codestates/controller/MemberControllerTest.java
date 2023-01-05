package com.codestates.controller;

import com.codestates.member.dto.MemberDto;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;  //일종의 Spring MVC 테스트 프레임워크 => WAS없이 Controller 테스트 환경 제공
    @Autowired
    private Gson gson;


    @Test
    void postMemberTest() throws Exception {

        //given (RequestBody 생성)
        MemberDto.Post post =
                new MemberDto.Post("hgd@mail.comn", "홍길동", "010-1234-1234");

        String content = gson.toJson(post);  //json으로 요청을 받기때문에 json으로 변환

        //when (request 수행)
        ResultActions actions =
                mockMvc.perform(
                        post("/v11/members") //url + http method
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)  //request body
                );

        //then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

}
