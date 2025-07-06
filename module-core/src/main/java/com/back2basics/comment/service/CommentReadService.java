package com.back2basics.comment.service;

import com.back2basics.comment.port.in.CommentReadUseCase;
import com.back2basics.comment.port.out.CommentReadPort;
import com.back2basics.comment.service.result.CommentReadResult;
import com.back2basics.infra.validator.PostValidator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentReadService implements CommentReadUseCase {

    private final PostValidator postValidator;
    private final CommentReadPort commentReadPort;

    @Override
    public List<CommentReadResult> getCommentsByPostId(Long userId, Long postId) {
        postValidator.findPost(postId);
        
        return commentReadPort.findAllCommentsByPostId(postId).stream()
            .map(CommentReadResult::toResult)
            .collect(Collectors.toList());
    }

}
