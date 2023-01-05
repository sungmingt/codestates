package com.codestates.event;

import com.codestates.helper.EmailSender;
import com.codestates.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
@Slf4j
public class EventHandler {

    private final EmailSender emailSender;
    private final MemberService memberService;

    public EventHandler(EmailSender emailSender, MemberService memberService) {
        this.emailSender = emailSender;
        this.memberService = memberService;
    }

    @EventListener  // @TransactionalEventListener도 사용가능.
    @Async
    public void handleEmailSending(EventPublisher publisher) {

        try {
            emailSender.sendEmail("any email message");
        } catch (Exception e) {
            log.error("MailSendException happened: ", e);
            memberService.deleteMember(publisher.getMember().getMemberId());
            throw new RuntimeException(e);
        }
    }
}
