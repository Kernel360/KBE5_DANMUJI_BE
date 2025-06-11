package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.infra.validation.validator.ProjectValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostReadUseCase;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.service.result.PostReadResult;
import com.back2basics.projectstep.port.out.ReadProjectStepPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostReadService implements PostReadUseCase {

    private final PostReadPort postReadPort;
    private final PostValidator postValidator;
    private final ProjectValidator projectValidator;
    private final ReadProjectStepPort readProjectStepPort;

    @Override
    public PostReadResult getPost(Long userId, Long postId) {
        Post post = postValidator.findPost(postId);
        return PostReadResult.toResult(post);
    }

    @Override
    public Page<PostReadResult> getPostList(Long userId, Long projectId, Long projectStepId,
        Pageable pageable) {
        projectValidator.findProjectById(projectId);
        readProjectStepPort.findById(projectStepId);
        return postReadPort.findAllWithPaging(projectId, projectStepId, pageable)
            .map(PostReadResult::toResult);
    }

}
