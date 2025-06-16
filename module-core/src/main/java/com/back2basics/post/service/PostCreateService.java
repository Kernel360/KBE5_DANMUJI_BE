package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.port.in.PostCreateUseCase;
import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.port.out.PostCreatePort;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.project.model.Project;
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
        String userIp,
        PostCreateCommand command) {
        User user = userQueryPort.findById(userId);
        Project project = projectValidator.findProjectById(projectId);

        if (command.getParentId() != null) { // todo : validate로 옮기기
            postValidator.findPost(command.getParentId()).getId();
        }

        Post post = Post.builder()
            .parentId(command.getParentId())
            .authorIp(userIp)
            .author(user)
            .projectId(project.getId())
            .projectStepId(projectStepId)
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
