package com.codestates.repository;

import com.codestates.member.entity.Member;
import com.codestates.member.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
//MemberRepository 의 기능을 사용하기 위한 Configuration 자동 추가
//@Transactional 애너테이션을 포함  =>  하나의 테스트 케이스 실행이 종료되는 시점에 데이터베이스에 저장된 데이터는 rollback 처리
public class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test  //회원 저장 테스트
    public void saveMemberTest() {
        //given
        Member member = new Member();
        member.setEmail("hgd@gmail.com");
        member.setName("홍길동");
        member.setPhone("010-1111-2222");

        //when
        Member savedMember = memberRepository.save(member);

        //then
        assertNotNull(savedMember);
        assertEquals(member.getEmail(), savedMember.getEmail());
        assertEquals(member.getName(), savedMember.getName());
        assertEquals(member.getPhone(), savedMember.getPhone());
    }

    @Test  //Email 로 회원 검색 테스트
    public void findByEmailTest() {
        //given
        Member member = new Member();
        member.setEmail("hgd@gmail.com");
        member.setName("홍길동");
        member.setPhone("010-1111-2222");

        //when
        Member savedMember = memberRepository.save(member);
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());

        //then
        assertTrue(findMember.isPresent());
        assertEquals(findMember.get().getEmail(), member.getEmail());
        assertEquals(findMember.get().getName(), member.getName());
    }
}
