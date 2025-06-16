package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.post.model.Post;
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
    private final ProjectValidator projectValidator;
    private final UserQueryPort userQueryPort;
    private final PostValidator postValidator;

    @Override
    public PostCreateResult createPost(Long userId, Long projectId, Long projectStepId,
        String userIp, PostCreateCommand command) {

        User user = userQueryPort.findById(userId);
        projectValidator.findProjectById(projectId);
        postValidator.findParentPost(command.getParentId());

        Post post = Post.create(command, user, userIp);

        postCreatePort.save(post);
        return PostCreateResult.toResult(post);
    }
}
