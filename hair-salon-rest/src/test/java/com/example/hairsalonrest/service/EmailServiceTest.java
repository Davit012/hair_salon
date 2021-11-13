package com.example.hairsalonrest.service;

import com.example.hairsalonrest.HairSalonRestApplication;
import com.hairsaloncommon.service.EmailService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = HairSalonRestApplication.class)
public class EmailServiceTest {

    @MockBean
    private MailSender mailSender;

    @Autowired
    private EmailService emailService;

    @Test
    public void sendMessageTest() {
        String to = "test@gmail.com";
        String subject = "TestMessage";
        String text = "Test Text";
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo(to);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(text);
        emailService.sendMessage(to, subject, text);
        verify(mailSender).send(simpleMailMessage);
    }

}
