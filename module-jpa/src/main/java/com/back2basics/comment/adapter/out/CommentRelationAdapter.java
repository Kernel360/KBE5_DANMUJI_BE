package com.back2basics.comment.adapter.out;

import com.back2basics.comment.entity.CommentEntity;
import com.back2basics.comment.repository.CommentEntityRepository;
import com.back2basics.post.entity.PostEntity;
import com.back2basics.post.repository.PostEntityRepository;
import com.back2basics.service.comment.exception.CommentErrorCode;
import com.back2basics.service.comment.exception.CommentException;
import com.back2basics.service.post.exception.PostErrorCode;
import com.back2basics.service.post.exception.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CommentRelationAdapter {

    private final PostEntityRepository postEntityRepository;
    private final CommentEntityRepository commentRepository;

    public void assignRelations(CommentEntity entity, Long postId, Long parentId) {
        assignPostEntity(entity, postId);
        assignParentIfPresent(entity, parentId);
    }

    private void assignPostEntity(CommentEntity entity, Long postId) {
        PostEntity postEntity = findPostEntity(postId);
        entity.assignPost(postEntity);
    }

    private void assignParentIfPresent(CommentEntity entity, Long parentId) {
        if (parentId != null) {
            assignParentCommentEntity(entity, parentId);
        }
    }

    private void assignParentCommentEntity(CommentEntity entity, Long parentId) {
        CommentEntity parentEntity = findParentEntity(parentId);
        entity.assignParentComment(parentEntity);
        parentEntity.addChildComment(entity);
    }

    private PostEntity findPostEntity(Long postId) {
        return postEntityRepository.findById(postId)
            .orElseThrow(() -> new PostException(PostErrorCode.POST_NOT_FOUND));
    }

    private CommentEntity findParentEntity(Long parentId) {
        return commentRepository.findById(parentId)
            .orElseThrow(() -> new CommentException(CommentErrorCode.COMMENT_NOT_FOUND));
    }
}