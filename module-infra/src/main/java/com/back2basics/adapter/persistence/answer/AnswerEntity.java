package com.back2basics.adapter.persistence.answer;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.adapter.persistence.user.entity.UserEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "answers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_ip")
    private String authorIp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private UserEntity author;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @Column(name = "parent_answer_id")
    private Long parentId;

    @Builder
    public AnswerEntity(Long id, String authorIp, UserEntity author, String content,
        QuestionEntity question,
        Long parentId) {
        this.id = id;
        this.authorIp = authorIp;
        this.author = author;
        this.content = content;
        this.question = question;
        this.parentId = parentId;
    }

    public void assignQuestion(QuestionEntity question) {
        this.question = question;
    }
}
