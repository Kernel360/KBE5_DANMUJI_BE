package com.back2basics.question.service;

import com.back2basics.question.model.Question;
import com.back2basics.question.port.in.QuestionCreateUseCase;
import com.back2basics.question.port.in.command.QuestionCreateCommand;
import com.back2basics.question.port.out.QuestionCreatePort;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuestionCreateService implements QuestionCreateUseCase {

    private final QuestionCreatePort questionCreatePort;
    private final UserQueryPort userQueryPort;

    @Override
    public Long create(Long userId, String userIp, QuestionCreateCommand command) {
        User user = userQueryPort.findById(userId);
        Question question = Question.builder()
            .postId(command.getPostId())
            .authorIp(userIp)
            .author(user)
            .content(command.getContent())
            .build();

        Long savedId = questionCreatePort.save(question);
        return savedId;
    }
}
