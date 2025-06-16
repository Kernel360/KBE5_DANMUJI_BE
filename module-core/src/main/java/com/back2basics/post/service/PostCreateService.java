package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.infra.validation.validator.UserValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostCreateUseCase;
import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.port.out.PostCreatePort;
import com.back2basics.post.service.result.PostCreateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCreateService implements PostCreateUseCase {

    private final PostCreatePort postCreatePort;
    private final ProjectValidator projectValidator;
    private final PostValidator postValidator;
    private final UserValidator userValidator;

    @Override
    public PostCreateResult createPost(Long userId, Long projectId, Long projectStepId,
        String userIp, PostCreateCommand command) {

        userValidator.validateNotFoundUserId(userId);
        projectValidator.findProjectById(projectId);
        postValidator.findParentPost(command.getParentId());

        Post post = Post.create(command, userId, userIp);

        postCreatePort.save(post);
        return PostCreateResult.toResult(post);
    }
}
