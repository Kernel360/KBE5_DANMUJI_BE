package com.back2basics.adapter.persistence.comment.adapter;

import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentCreatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentCreateJpaAdapter implements CommentCreatePort {

    private final CommentEntityRepository commentRepository;
    private final CommentMapper mapper;

    @Override
    public Long save(Comment comment) {
        return commentRepository.save(mapper.toEntity(comment)).getId();
    }

}
