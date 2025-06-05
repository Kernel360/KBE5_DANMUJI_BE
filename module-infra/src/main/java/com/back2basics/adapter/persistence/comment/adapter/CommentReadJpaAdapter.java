package com.back2basics.adapter.persistence.comment.adapter;

import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentReadPort;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentReadJpaAdapter implements CommentReadPort {

    private final CommentEntityRepository commentEntityRepository;
    private final CommentMapper mapper;

    @Override
    public Optional<Comment> findById(Long id) {
        return commentEntityRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Comment> findAllCommentsByPostId(Long postId) {
        return commentEntityRepository.findAllCommentsByPostId(postId).stream()
            .map(mapper::toDomain).toList();
    }
}
