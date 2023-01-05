package com.codestates.helper.email;

import org.springframework.scheduling.annotation.Async;

public class MockEmailSendable implements EmailSendable {
    @Override
    public void send(String message) {
        System.out.println("Sent email");
    }
}
