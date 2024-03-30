package com.example.demo.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class MailRequest {
    private String to;
    private String subject;
    private String text;
    private String pathToAttachment;

    public MailRequest(String to, String subject, String text) {
        this.to = to;
        this.subject = subject;
        this.text = text;
    }

    public MailRequest() {
    }

    public MailRequest(String to, String subject, String text, String pathToAttachment) {
        this.to = to;
        this.subject = subject;
        this.text = text;
        this.pathToAttachment = pathToAttachment;
    }
}
