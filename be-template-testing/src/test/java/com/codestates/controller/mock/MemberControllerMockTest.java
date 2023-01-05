package com.codestates.controller.mock;

import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import com.codestates.member.mapper.MemberMapper;
import com.codestates.member.service.MemberService;
import com.codestates.stamp.Stamp;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest  //SpringBoot 기반의 애플리케이션을 테스트하기 위한 ApplicationContext 를 생성한다.
@AutoConfigureMockMvc //Controller 테스트를 위한 애플리케이션의 자동 구성 작업을 해주며, MockMvc 같은 기능을 사용하기 위해서는 너테이션을 반드시 추가해 주어야 한다.
public class MemberControllerMockTest {

    @Autowired
    private MockMvc mockMvc;  //일종의 Spring MVC 테스트 프레임워크 => WAS 없이 Controller 테스트환경 제공
    @Autowired
    private Gson gson;

    @MockBean  //ApplicationContext 에 등록되어 있는 Bean 에 대한 Mockito Mock 객체를 생성하고 주입해준다.
    private MemberService memberService;
    @Autowired
    private MemberMapper mapper;

    @Test
    void postMemberTest() throws Exception {
        //given
        MemberDto.Post post = new MemberDto.Post("hgd@gmail.com", "홍길동", "010-1234-5678");

        Member member = mapper.memberPostToMember(post);
        member.setStamp(new Stamp());

        given(memberService.createMember(Mockito.any(Member.class))) //Stubbing 메서드
                .willReturn(member);

        String content = gson.toJson(post);  //json으로 요청을 받기때문에 json으로 변환하여 요청을 보낸다.

        //when
        ResultActions actions = mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );
        //then
        MvcResult result = actions
                .andExpect(status().isCreated())
                // 리턴하는 response body(JSON 형식)의 각 프로퍼티(email, name, phone)의 값을 검증하는 기능을 추가
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.phone").value(post.getPhone()))
                .andReturn();
    }
}
