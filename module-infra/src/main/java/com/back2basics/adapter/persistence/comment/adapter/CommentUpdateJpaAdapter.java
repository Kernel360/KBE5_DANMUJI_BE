package com.back2basics.adapter.persistence.comment.adapter;

import com.back2basics.adapter.persistence.comment.CommentEntity;
import com.back2basics.adapter.persistence.comment.CommentEntityRepository;
import com.back2basics.adapter.persistence.comment.utils.CommentRelationHelper;
import com.back2basics.adapter.persistence.comment.utils.CommentUpdateHelper;
import com.back2basics.comment.model.Comment;
import com.back2basics.comment.port.out.CommentUpdatePort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentUpdateJpaAdapter implements CommentUpdatePort {

    private final CommentEntityRepository commentRepository;
    private final CommentUpdateHelper commentUpdateHelper;
    private final CommentRelationHelper commentRelationHelper;

    @Override
    public void update(Comment comment) {
        CommentEntity entity = commentUpdateHelper.updateComment(comment.getId(), comment);
        commentRelationHelper.assignRelations(entity, comment.getPostId(),
            comment.getParentCommentId());

        commentRepository.save(entity);
    }
}
