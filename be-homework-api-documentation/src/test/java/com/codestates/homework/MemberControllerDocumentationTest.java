package com.codestates.homework;

import com.codestates.member.controller.MemberController;
import com.codestates.member.dto.MemberDto;
import com.codestates.member.entity.Member;
import com.codestates.member.mapper.MemberMapper;
import com.codestates.member.service.MemberService;
import com.codestates.stamp.Stamp;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.codestates.member.entity.Member.MemberStatus.*;
import static com.codestates.util.ApiDocumentUtils.getRequestPreProcessor;
import static com.codestates.util.ApiDocumentUtils.getResponsePreProcessor;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@MockBean(JpaMetamodelMappingContext.class)
@AutoConfigureRestDocs
public class MemberControllerDocumentationTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberMapper mapper;

    @Autowired
    private Gson gson;

    @Test
    public void getMemberTest() throws Exception {
        // TODO 여기에 MemberController의 getMember() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.

        // given
        long memberId = 1L;
        Member member = new Member("hgd@gmail.com", "홍길동", "010-1111-1111"); //검증용 데이터

        MemberDto.response responseDto =
                new MemberDto.response(1L,
                        "hgd@gmail.com",
                        "홍길동",
                        "010-1111-1111",
                        MEMBER_ACTIVE,
                        new Stamp());

        given(memberService.findMember(Mockito.anyLong())).willReturn(new Member());
        given(mapper.memberToMemberResponse(Mockito.any(Member.class))).willReturn(responseDto);

        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/v11/members/{memberId}", memberId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value(member.getEmail()))
                .andExpect(jsonPath("$.data.name").value(member.getName()))
                .andExpect(jsonPath("$.data.phone").value(member.getPhone()))
                .andDo(document("get-member",  //문서 스니핏 식별자
                        getResponsePreProcessor(),
                        pathParameters(
                                parameterWithName("memberId").description("회원 식별자")
                        ),
                        responseFields(
                                List.of(
                                        fieldWithPath("data").type(JsonFieldType.OBJECT).description("결과 데이터"),
                                        fieldWithPath("data.memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data.name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data.phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
                                        fieldWithPath("data.memberStatus").type(JsonFieldType.STRING).description("회원 상태: 활동중 / 휴면 상태 / 탈퇴 상태"),
                                        fieldWithPath("data.stamp").type(JsonFieldType.NUMBER).description("스탬프 갯수")
                                )
                        )
                ));
    }

    @Test
    public void getMembersTest() throws Exception {
        // TODO 여기에 MemberController의 getMembers() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.

        //given
        int page = 1;
        int size = 3;

        List<Member> members = List.of(new Member("member1@gmail.com", "member1", "010-1111-1111"),
                new Member("member2@gmail.com", "member2", "010-2222-2222"),
                new Member("member3@gmail.com", "member3", "010-3333-3333"));

        Page<Member> memberPage = new PageImpl<>(
                members, PageRequest.of(page-1, size, Sort.by("memberId").descending()), members.size());

        List<MemberDto.response> responses = List.of(
                new MemberDto.response(1L, "member1@gmail.com", "member1", "010-1111-1111", MEMBER_ACTIVE, new Stamp()),
                new MemberDto.response(2L, "member2@gmail.com", "member2", "010-2222-2222", MEMBER_ACTIVE, new Stamp()),
                new MemberDto.response(3L, "member3@gmail.com", "member3", "010-3333-3333", MEMBER_ACTIVE, new Stamp()));

        given(memberService.findMembers(Mockito.anyInt(), Mockito.anyInt())).willReturn(memberPage);
        given(mapper.membersToMemberResponses(Mockito.any())).willReturn(responses);


        // when
        ResultActions actions =
                mockMvc.perform(
                        get("/v11/members")
                                .param("page", String.valueOf(page))
                                .param("size", String.valueOf(size))
                                .accept(MediaType.APPLICATION_JSON)
                );

        // then
        actions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(3)))  //
                .andDo(document("get-members",
                        getResponsePreProcessor(),
                        requestParameters(
                                parameterWithName("page").description("page index"),
                                parameterWithName("size").description("page size")
                        ),
                        responseFields(
                                List.of(
                                        //data
                                        fieldWithPath("data").type(JsonFieldType.ARRAY).description("결과 데이터"),
                                        fieldWithPath("data[].memberId").type(JsonFieldType.NUMBER).description("회원 식별자"),
                                        fieldWithPath("data[].email").type(JsonFieldType.STRING).description("이메일"),
                                        fieldWithPath("data[].name").type(JsonFieldType.STRING).description("이름"),
                                        fieldWithPath("data[].phone").type(JsonFieldType.STRING).description("휴대폰 번호"),
                                        fieldWithPath("data[].memberStatus").type(JsonFieldType.STRING).description("회원 상태: 활동중 / 휴면 상태 / 탈퇴 상태"),
                                        fieldWithPath("data[].stamp").type(JsonFieldType.NUMBER).description("스탬프 갯수"),
                                        //pageInfo
                                        fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("page information"),
                                        fieldWithPath("pageInfo.page").type(JsonFieldType.NUMBER).description("page index"),
                                        fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("page size"),
                                        fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("total elements"),
                                        fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("total pages")
                                )
                        )
                ));
    }

    @Test
    public void deleteMemberTest() throws Exception {
        // TODO 여기에 MemberController의 deleteMember() 핸들러 메서드 API 스펙 정보를 포함하는 테스트 케이스를 작성 하세요.

        // given
        long memberId = 1L;

        doNothing().when(memberService).deleteMember(memberId);

        // when
        ResultActions actions =
                mockMvc.perform(
                        delete("/v11/members/{memberId}", memberId)
                );

        // then
        actions
                .andExpect(status().isNoContent())
                .andDo(document("delete-member",
                        pathParameters(
                                parameterWithName("memberId").description("회원 식별자")
                        )
                ));
    }
}
