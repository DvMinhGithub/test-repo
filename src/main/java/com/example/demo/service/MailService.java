package com.example.demo.service;

import com.example.demo.model.request.MailRequest;
import com.example.demo.model.response.ResponseApi;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.io.File;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    @Value("{spring.mail.username}")
    private String mailUsername;

    @Value("{${spring.mail.template}}")
    private String mailTemplate;

    private final SpringTemplateEngine springTemplateEngine;

    public MailService(JavaMailSender javaMailSender, SpringTemplateEngine springTemplateEngine) {
        this.javaMailSender = javaMailSender;
        this.springTemplateEngine = springTemplateEngine;
    }

    public ResponseEntity<ResponseApi<?>> sendTextMessage(MailRequest mailRequest) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(mailUsername);
            message.setTo(mailRequest.getTo());
            message.setSubject(mailRequest.getSubject());
            message.setText(mailRequest.getText());
            javaMailSender.send(message);
            return new ResponseEntity<>(new ResponseApi<>("Send mail success", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi<>("Internal server error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    public ResponseEntity<ResponseApi<?>> sendCustomMessageWithAttachment(MailRequest mailRequest) {
        try {
            Context thymeleafContext = new Context();
            thymeleafContext.setVariable("message", mailRequest.getText());
            String htmlContent = springTemplateEngine.process(mailTemplate, thymeleafContext);

            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom(mailUsername);
            helper.setTo(mailRequest.getTo());
            helper.setSubject(mailRequest.getSubject());
            helper.setText(htmlContent, true);

            if (mailRequest.getPathToAttachment() != null) {
                FileSystemResource file
                        = new FileSystemResource(new File(mailRequest.getPathToAttachment()));
                helper.addAttachment(file.getFilename(), file);
            }

            javaMailSender.send(message);
            return new ResponseEntity<>(new ResponseApi<>("Send mail success", 200), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new ResponseApi<>("Internal server error", 500), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
