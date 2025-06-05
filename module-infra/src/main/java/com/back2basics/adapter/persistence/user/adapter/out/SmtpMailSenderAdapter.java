package com.back2basics.adapter.persistence.user.adapter.out;

import com.back2basics.user.port.out.MailSenderPort;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SmtpMailSenderAdapter implements MailSenderPort {

    private final JavaMailSender javaMailSender;

    @Override
    public void sendMail(String to, String username, String link) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject("[Danmuji] 비밀번호 재설정 링크");
        message.setText(String.format("""
            안녕하세요 %s님,
            
            비밀번호 재설정을 위해 아래 링크를 클릭해주세요:
            %s
            
            이 링크는 30분 후 만료됩니다.
            """, username, link));
        javaMailSender.send(message);
    }
}
