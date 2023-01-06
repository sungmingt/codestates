package com.codestates.member.dto;

import lombok.Getter;

@Getter
public class MemberAllDto<T> {

    private T data;
    private PageInfo pageInfo;

    public MemberAllDto(T data, PageInfo pageInfo) {
        this.data = data;
        this.pageInfo = pageInfo;
    }
}
