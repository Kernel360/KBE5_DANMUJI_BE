package com.back2basics.user.port.out;

public interface MailSenderPort {

    void sendMail(String to, String username, String link);
}
