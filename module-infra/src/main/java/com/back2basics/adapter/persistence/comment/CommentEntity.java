package com.back2basics.adapter.persistence.comment;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    private PostEntity post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_comment_id")
    private CommentEntity parentCommentId;

    @OneToMany(mappedBy = "parentCommentId", cascade = CascadeType.ALL)
    private List<CommentEntity> childrenComments = new ArrayList<>();

    @Builder
    public CommentEntity(Long id, UserEntity author, String content, PostEntity post,
        CommentEntity parentComment) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.post = post;
        this.parentCommentId = parentComment;
    }

    public void assignPost(PostEntity post) {
        this.post = post;
    }

    public void assignParentComment(CommentEntity parent) {
        this.parentCommentId = parent;
    }

    public void addChildComment(CommentEntity child) {
        childrenComments.add(child);
        child.assignParentComment(this);
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
