package com.codestates.homework;

import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import com.codestates.member.mapper.MemberMapper;
import com.codestates.member.service.MemberService;
import com.codestates.stamp.Stamp;
import com.google.gson.Gson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@SpringBootTest
@AutoConfigureMockMvc
public class MemberControllerHomeworkTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private Gson gson;
    @MockBean
    private MemberService memberService;
    @Autowired
    private MemberMapper mapper;

    @Test
    void postMemberTest() throws Exception {
        // TODO MemberController의 postMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^

        //given
        MemberDto.Post post =
                new MemberDto.Post("postMember@gmail.com", "postMember", "010-1234-5678");

        Member member = mapper.memberPostToMember(post);
        member.setStamp(new Stamp());

        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(member);

        String content = gson.toJson(member);

        //when
        ResultActions actions = mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.email").value(post.getEmail()))
                .andExpect(jsonPath("$.data.name").value(post.getName()))
                .andExpect(jsonPath("$.data.phone").value(post.getPhone()))
                .andReturn();
    }
    @Test
    @DisplayName("postMember validation test")
    void postMemberTest2() throws Exception {

        //given (invalid email format)
        MemberDto.Post post =
                new MemberDto.Post("email", "postMember", "010-1234-5678");

        Member member = mapper.memberPostToMember(post);
        member.setStamp(new Stamp());

        given(memberService.createMember(Mockito.any(Member.class)))
                .willReturn(member);
        String content = gson.toJson(member);

        //when
        ResultActions actions = mockMvc.perform(
                post("/v11/members")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );
        //then
        actions
                .andExpect(status().isBadRequest());
    }

    @Test
    void patchMemberTest() throws Exception {
        // TODO MemberController의 patchMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^

        //given
        MemberDto.Patch patch = new MemberDto.Patch(
                1L, "updatedMember", "010-7777-7777", Member.MemberStatus.MEMBER_SLEEP);

        Member member = mapper.memberPatchToMember(patch);
        member.setStamp(new Stamp());

        given(memberService.updateMember(Mockito.any(Member.class)))
                .willReturn(member);

        String content = gson.toJson(member);

        //when
        ResultActions actions = mockMvc.perform(
                patch("/v11/members/{memberId}", member.getMemberId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(patch.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(patch.getName()))
                .andExpect(jsonPath("$.data.phone").value(patch.getPhone()))
                .andReturn();
    }

    @Test
    void getMemberTest() throws Exception {
        // TODO MemberController의 getMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^

        //given
        Member member = new Member(1L, "getMember@gmail.com", "getMember", "010-1234-5678");
        member.setStamp(new Stamp());

        given(memberService.findMember(Mockito.anyLong()))
                .willReturn(member);

        String content = gson.toJson(member);

        //when
        ResultActions actions = mockMvc.perform(
                get("/v11/members/{memberId}", member.getMemberId())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(content)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.memberId").value(member.getMemberId()))
                .andExpect(jsonPath("$.data.name").value(member.getName()))
                .andExpect(jsonPath("$.data.phone").value(member.getPhone()))
                .andReturn();
    }

    @Test
    void getMembersTest() throws Exception {
        // TODO MemberController의 getMembers() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^

        //given (findMembers 반환객체 생성)
        Member member1 = new Member(1L, "member1@gmail.com", "member1", "010-1111-1111");
        Member member2 = new Member(2L, "member2@gmail.com", "member2", "010-2222-2222");
        member1.setStamp(new Stamp());
        member2.setStamp(new Stamp());

        int page = 1;
        int size = 2;

        List<Member> members = List.of(member1, member2);
        Page<Member> pageMembers = new PageImpl<>(members, PageRequest.of(page, size,
                Sort.by("memberId").descending()), members.size());

        given(memberService.findMembers(Mockito.anyInt(), Mockito.anyInt()))
                .willReturn(pageMembers);

        String content = gson.toJson(pageMembers);

        //when
        ResultActions actions = mockMvc.perform(
                get("/v11/members")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2))) // 회원 수 검증
                .andReturn();
    }

    @Test
    void deleteMemberTest() throws Exception {
        // TODO MemberController의 deleteMember() 핸들러 메서드를 테스트하는 테스트 케이스를 여기에 작성하세요.
        // TODO Mockito를 사용해야 합니다. ^^

        //given
        Member member = new Member(1L, "deleteMember@gmail.com", "deleteMember", "010-1234-5678");
        member.setStamp(new Stamp());

        doNothing().when(memberService).deleteMember(member.getMemberId());

        //when
        ResultActions actions = mockMvc.perform(
                delete("/v11/members/{memberId}", member.getMemberId())
        );

        //then
        actions
                .andExpect(status().isNoContent())
                .andReturn();
    }
}
