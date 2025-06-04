package com.back2basics.post.service;

import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.port.in.PostCreateUseCase;
import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.port.out.PostCreatePort;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.user.model.User;
import com.back2basics.user.port.out.UserQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCreateService implements PostCreateUseCase {

    private final PostCreatePort postCreatePort;
    private final UserQueryPort userQueryPort;

    @Override
    public PostCreateResult createPost(PostCreateCommand command) {
        User user = userQueryPort.findById(command.getAuthorId());
        Post post = Post.builder()
            .author(user)
            .title(command.getTitle())
            .content(command.getContent())
            .type(command.getType())
            .priority(command.getPriority())
            .status(PostStatus.PENDING)
            .completedAt(null)
            .deletedAt(null)
            .build();

        postCreatePort.save(post);
        return PostCreateResult.toResult(post);
    }
}
