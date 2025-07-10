package com.back2basics.comment.service;

import com.back2basics.comment.port.in.CommentUpdateUseCase;
import com.back2basics.comment.port.in.command.CommentUpdateCommand;
import com.back2basics.comment.service.utils.CommentUpdateProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUpdateService implements CommentUpdateUseCase {

    private final CommentUpdateProcessor processor;

    @Override
    public void updateComment(Long userId, String userIp, Long commentId,
        CommentUpdateCommand command) {
        processor.update(userId, userIp, commentId, command);
    }
}
