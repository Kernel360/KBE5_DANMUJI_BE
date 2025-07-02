package com.back2basics.board.post.service;

import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.in.PostRestoreUseCase;
import com.back2basics.board.post.port.out.PostRestorePort;
import com.back2basics.history.model.DomainType;
import com.back2basics.history.service.HistoryLogService;
import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostRestoreService implements PostRestoreUseCase {

    private final PostRestorePort postRestorePort;
    private final PostValidator postValidator;
    private final HistoryLogService historyLogService;
    private final UserValidator userValidator;


    @Override
    public void restorePost(Long requesterId, Long postId) {
        userValidator.isAdmin(requesterId);

        Post post = postValidator.isDeleted(postId);
        postValidator.isAuthor(post, requesterId);

        post.restore();
        postRestorePort.restorePost(post);

        historyLogService.logRestored(DomainType.POST, requesterId, post, "비활성화 게시글 복구");
    }
}
