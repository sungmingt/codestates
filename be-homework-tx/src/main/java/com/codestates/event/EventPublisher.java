package com.codestates.event;

import com.codestates.member.entity.Member;
import lombok.Getter;

@Getter
public class EventPublisher {

    private Member member;
    public EventPublisher(Member member) {
        this.member = member;
    }
}
