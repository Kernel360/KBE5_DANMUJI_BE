package com.back2basics.comment.service;

import com.back2basics.comment.port.in.CommentCreateUseCase;
import com.back2basics.comment.port.in.command.CommentCreateCommand;
import com.back2basics.comment.service.utils.CommentCreateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentCreateService implements CommentCreateUseCase {

    private final CommentCreateProcessor processor;

    @Override
    public Long createComment(Long userId, String userIp, CommentCreateCommand command) {
        return processor.create(userId, userIp, command);
    }
}
