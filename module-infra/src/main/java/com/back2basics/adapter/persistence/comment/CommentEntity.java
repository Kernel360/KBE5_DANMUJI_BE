package com.back2basics.adapter.persistence.comment;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.comment.model.Comment;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "comments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_ip")
    private String authorIp;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "parent_comment_id")
    private Long parentId;

    public static CommentEntity of(Comment comment) {
        CommentEntity entity = new CommentEntity();
        entity.id = comment.getId();
        entity.postId = comment.getPostId();
        entity.parentId = comment.getParentId();
        entity.authorIp = comment.getAuthorIp();
        entity.authorId = comment.getAuthorId();
        entity.content = comment.getContent();
        return entity;
    }

}
