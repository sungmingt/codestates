package com.codestates.homework;

import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import com.codestates.member.repository.MemberRepository;
import com.codestates.stamp.Stamp;
import com.google.gson.Gson;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MemberControllerHomeworkTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @Autowired
    private MemberRepository memberRepository;

    //BeforeEach는 매 테스트마다 롤백되지 않는다 -> @BeforeAll 사용
    @BeforeAll
    void beforeAll() {
        Member member1 = new Member("member1@gmail.com", "멤버1", "010-1111-1111");
        Member member2 = new Member("member2@gmail.com", "멤버2", "010-2222-2222");
        Member member3 = new Member("member3@gmail.com", "멤버3", "010-3333-3333");
        member1.setStamp(new Stamp());
        member2.setStamp(new Stamp());
        member3.setStamp(new Stamp());
        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);
    }

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
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.phone").value(post.getPhone()))
                .andReturn();

//        System.out.println(result.getResponse().getContentAsString());
    }
    @Test
    @DisplayName("post 유효성 검증 동작 테스트")
    void postMemberTest2() throws Exception {
        // given
        MemberDto.Post post = new MemberDto.Post("member1@gmail.com",  //중복 이메일 사용
                "새멤버",
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
                .andExpect(status().is(409)) //409 = MEMBER_EXISTS
                .andReturn();
    }

    @Test
    void patchMemberTest() throws Exception {
        // TODO MemberController의 patchMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        //=========patch 동작 테스트===========

        //given
        long memberId = 1;
        MemberDto.Patch patch = new MemberDto.Patch(
                1, "수정",
                "010-1234-1234", Member.MemberStatus.MEMBER_ACTIVE);

        String content = gson.toJson(patch);

        //when
        ResultActions actions =
                mockMvc.perform(
                        patch("/v11/members/{memberId}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                );

        // then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.phone").value(patch.getPhone()))
                .andExpect(jsonPath("$.data.memberStatus").value(patch.getMemberStatus().getStatus()))
                .andReturn();
    }

    @Test
    void getMemberTest() throws Exception {
        // TODO MemberController의 getMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        //given
        // BeforeEach에서 DB에 저장한 값
        long memberId = 2;
        Member member2 = new Member("member2@gmail.com", "멤버2", "010-2222-2222");

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/v11/members/{memberId}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        MvcResult result = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value(member2.getName()))
                .andExpect(jsonPath("$.data.phone").value(member2.getPhone()))
                .andExpect(jsonPath("$.data.email").value(member2.getEmail()))
                .andReturn();
    }

    @Test
    void getMembersTest() throws Exception {
        // TODO MemberController의 getMembers() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        //given
        //DB에 저장된 회원 수 : 3명

        //DB에 저장된 값
        Member member3 = new Member("member3@gmail.com", "멤버3", "010-3333-3333");

        //when
        ResultActions actions =
                mockMvc.perform(
                        get("/v11/members")
                                .accept(MediaType.APPLICATION_JSON)
                                .param("page", "1")
                                .param("size", "3")
                );

        // then
        MockHttpServletResponse response = actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3))) //회원 수 검증
                //회원정보 조회 검증 (역순으로 조회되기 때문에 첫번째 값이 member3여야 한다)
                .andExpect(jsonPath("$.data[0].email").value(member3.getEmail()))
                .andReturn()
                .getResponse();

        System.out.println(response);
    }

    @Test
    void deleteMemberTest() throws Exception {
        // TODO MemberController의 deleteMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        //given
        //삭제할 값
        long memberId = 1;
        Member member1 = new Member("member1@gmail.com", "멤버1", "010-1111-1111");

        //when
        ResultActions actions =
                mockMvc.perform(
                        delete("/v11/members/{memberId}", memberId)
                );

        // then
        MvcResult result = actions
                .andExpect(status().isNoContent())  //204
                .andReturn();

        //삭제 확인
        assertFalse(memberRepository.findById(1L).isPresent());
    }
}
