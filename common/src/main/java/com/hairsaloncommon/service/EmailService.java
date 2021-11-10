package com.hairsaloncommon.service;

public interface EmailService {
    void sendMessage(String to, String subject, String message);
}
