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
    private final UserQueryPort userQueryPort;
    private final ProjectValidator projectValidator;
    private final PostValidator postValidator;

    @Override
    public PostCreateResult createPost(Long userId, String userIp, PostCreateCommand command) {
        // 유저는 포트에서 찾아오고 프로젝트는 validator에서 받아오게 돼있는 이유
        // 서로 인터페이스 구현체 내부의 로직이 다릅니다.
        // 유저는 포트에서 User타입 리턴, 어댑터(구현체)에서 orElseThrow해주고 있고
        // 프로젝트는 포트에서 Optional<Project> 타입 리턴, 어댑터(구현체)에서는 orElseThrow를 하지 않습니다 validator에서 throw중
        // todo : 이것도 통일해야할 것 같습니다.
        User user = userQueryPort.findById(userId);
        Project project = projectValidator.findProjectById(command.getProjectId());
        Long parentPostId = postValidator.findPost(command.getParentId()).getId();

        Post post = Post.builder()
            .parentId(parentPostId)
            .authorIp(userIp)
            .author(user)
            .project(project)
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
