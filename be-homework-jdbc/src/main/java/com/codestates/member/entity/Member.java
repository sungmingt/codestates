package com.codestates.member.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 *  - 멤버 변수 추가
 *  - lombok 추가
 *  - Spring Data JDBC 엔티티 설정 추가
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    @Id
    private Long memberId;

    private String email;

    private String name;

    private String phone;
}
