package com.back2basics.board.post.service;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.PostDeleteUseCase;
import com.back2basics.board.post.port.out.PostSoftDeletePort;
import com.back2basics.history.model.History;
import com.back2basics.history.model.HistoryType;
import com.back2basics.history.port.out.HistoryCreatePort;
import com.back2basics.infra.validation.validator.PostValidator;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDeleteService implements PostDeleteUseCase {

    private final PostSoftDeletePort postSoftDeletePort;
    private final PostValidator postValidator;
    private final HistoryCreatePort historyCreatePort;

    @Override
    public void softDeletePost(Long requesterId, Long postId) {
        Post post = postValidator.findPost(postId);
        postValidator.isAuthor(post, requesterId);

        post.markDeleted();

        History history = History.create(
            HistoryType.POST_DELETED,
            "post",
            post.getId(),
            String.valueOf(post.getAuthorId()),
            Map.of(
                "before", post
            ),
            Map.of(
                "isDeleted", post.isDelete()
            ),
            LocalDateTime.now()
        );

        historyCreatePort.save(history);
        postSoftDeletePort.softDelete(post);
    }
}
