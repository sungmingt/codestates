package com.codestates.controller.mock;

import com.codestates.exception.BusinessLogicException;
import com.codestates.member.entity.Member;
import com.codestates.member.repository.MemberRepository;
import com.codestates.member.service.MemberService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

// Spring 을 사용하지 않고 Junit 에서 Mockito 의 기능을 사용할 수 있다.
@ExtendWith(MockitoExtension.class)
public class MemberServiceMockTest {

    @Mock //MemberService 에 주입
    private MemberRepository memberRepository;

    @InjectMocks  // @InjectMocks 애너테이션을 추가한 필드에 위에서 생성한 Mock 객체를 주입해 준다
    private MemberService memberService;

    @Test
    void createMemberTest() {

        //given
        Member member = new Member("hgd@gmail.com", "홍길동", "010-1111-1111");

        given(memberRepository.findByEmail(member.getEmail()))
                .willReturn(Optional.of(member));  //Stubbing 메서드

        //when / then
        assertThrows(BusinessLogicException.class, () -> memberService.createMember(member));
    }
}
