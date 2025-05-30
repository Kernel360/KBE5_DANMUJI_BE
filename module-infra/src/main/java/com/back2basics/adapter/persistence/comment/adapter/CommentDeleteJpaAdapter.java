package com.back2basics.adapter.persistence.comment.adapter;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.CommentMapper;
import com.back2basics.adapter.persistence.comment.utils.CommentDeleteHelper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentDeletePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentDeleteJpaAdapter implements CommentDeletePort {

    private final CommentEntityRepository commentRepository;
    private final CommentDeleteHelper commentDeleteHelper;
    private final CommentMapper mapper;

    @Override
    public void delete(Comment comment) {
        CommentEntity entity = mapper.fromDomain(comment);
        commentDeleteHelper.deleteCommentWithOrphanKeep(entity.getId());

        commentRepository.delete(entity);
    }
}
