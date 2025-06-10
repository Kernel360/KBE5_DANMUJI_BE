package com.back2basics.domain.user.dto.request;


import com.back2basics.user.port.in.command.SendMailCommand;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record SendMailRequest(
    @NotEmpty(message = "아이디는 필수입니다.") String username,
    @NotEmpty(message = "이메일은 필수입니다.") @Email(message = "유효한 이메일 형식이 아닙니다.") String email
) {

    public SendMailCommand toCommand() {
        return SendMailCommand.builder()
            .username(username)
            .email(email)
            .build();
    }
}
