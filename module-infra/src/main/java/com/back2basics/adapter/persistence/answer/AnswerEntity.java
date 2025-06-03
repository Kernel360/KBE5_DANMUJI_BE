package com.back2basics.adapter.persistence.answer;

import com.back2basics.adapter.persistence.common.entity.BaseTimeEntity;
import com.back2basics.adapter.persistence.question.QuestionEntity;
import com.back2basics.answer.model.Answer;
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
@Table(name = "answers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AnswerEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "author_id", nullable = false)
    private Long authorId;

    @Column(name = "content", nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private QuestionEntity question;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_answer_id")
    private AnswerEntity parentAnswerId;

    @OneToMany(mappedBy = "parentAnswerId", cascade = CascadeType.ALL)
    private List<AnswerEntity> childrenAnswers = new ArrayList<>();

    @Builder
    public AnswerEntity(Long id, Long authorId, String content, QuestionEntity question,
        AnswerEntity parentAnswer) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.question = question;
        this.parentAnswerId = parentAnswer;
    }

    public void assignQuestion(QuestionEntity question) {
        this.question = question;
    }

    public void assignParentAnswer(AnswerEntity parent) {
        this.parentAnswerId = parent;
    }

    public void addChildAnswer(AnswerEntity child) {
        childrenAnswers.add(child);
        child.assignParentAnswer(this);
    }

    public void update(Answer answer) {

        this.content = answer.getContent();
    }
}
