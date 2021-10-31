package com.example.hairsalonrest.service;

import org.junit.After;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmailServiceTest {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @After
    public void after() {
        jdbcTemplate.execute("delete from send_email");
    }

    public void sendMessageSuccess(){
        SendMessageDto sendMessageDto = createSendMessageDto();
        sendEmailService.sendEmailAsync(sendMessageDto);
        List<SendEmail> list = findAll();
        assertEquals(1, list.size());
        SendEmail sendEmail = list.get(0);
        assertEquals(SendEmailStatus.CREATED, sendEmail.getStatus());

        sendEmailService.sendEmails();

        sendEmail = findSendEmailById(sendEmail.getId());
        assertEquals(SendEmailStatus.SENT, sendEmail.getStatus());
        assertNotNull(sendEmail.getSent());
    }

    private SendMessageDto createSendMessageDto() {
        SendMessageDto sendEmailDto = new SendMessageDto();
        sendEmailDto.setFrom("from@mail.ru");
        sendEmailDto.setTo("to@mail.ru");
        sendEmailDto.setSubject("test subject");
        sendEmailDto.setMessage("test text");
        return sendEmailDto;
    }

}
