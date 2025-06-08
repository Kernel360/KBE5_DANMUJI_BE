package com.back2basics.adapter.persistence.comment.adapter;

import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentUpdateJpaAdapter implements CommentUpdatePort {

    private final CommentMapper mapper;
    private final CommentEntityRepository commentRepository;

    @Override
    public void update(Comment comment) {
        commentRepository.save(mapper.toEntity(comment));
    }
}
