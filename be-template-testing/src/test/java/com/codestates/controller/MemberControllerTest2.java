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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MemberControllerTest2 {

// MemberController에 대한 조금 더 확실한 테스트 검증을 위해서 MemberController의 postMember() 핸들러 메서드에서
// 리턴하는 response body(JSON 형식)의 각 프로퍼티(email, name, phone)의 값을 검증하는 기능을 추가

    //하지만 여기선 DB까지 불필요한 로직이 수행되고있음 -> Mock 객체(Mockito) 사용으로 해결

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;

    @Test
    void postMemberTest() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com",
                "홍길동",
                "010-1234-5678");
        String content = gson.toJson(post);

        // when
        ResultActions actions =
                mockMvc.perform(
                        post("/v11/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        MvcResult result = actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))  // (1)
                .andExpect(jsonPath("$.data.name").value(post.getName()))   // (2)
                .andExpect(jsonPath("$.data.phone").value(post.getPhone())) // (3)
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }
}