package com.back2basics.port.out.mail;

public interface MailSenderPort {

    void send(String to, String subject, String body);
}
