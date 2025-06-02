package com.back2basics.adapter.persistence.question;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.question.model.Question;
import com.back2basics.question.model.QuestionStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "questions")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class QuestionEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id", nullable = false)
    private Long postId;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    private QuestionStatus status;

    @Builder
    public QuestionEntity(Long id, Long postId, Long authorId, String content,
        QuestionStatus status, boolean isDeleted) {
        this.id = id;
        this.postId = postId;
        this.authorId = authorId;
        this.content = content;
        this.status = status;
    }

    public void update(Question question) {
        this.content = question.getContent();
        this.status = question.getStatus();
    }

}
