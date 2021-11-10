package com.example.hairsalonrest.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

    private MailSender mailSender;

    @Test
    public void sendMessageTest() {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setTo("test@gmail.com");
        simpleMailMessage.setSubject("TestMessage");
        simpleMailMessage.setText("Test Text");
        verify(mailSender).send(simpleMailMessage);
    }

}
