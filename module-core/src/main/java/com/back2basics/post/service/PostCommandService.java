package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.in.PostCommandUseCase;
import com.back2basics.post.port.in.dto.PostCreateCommand;
import com.back2basics.post.port.in.dto.PostUpdateCommand;
import com.back2basics.post.port.out.PostWritePort;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.post.service.result.PostDeleteResult;
import com.back2basics.post.service.result.PostUpdateResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostCommandService implements PostCommandUseCase {

    private final PostWritePort postWritePort;
    private final PostValidator postValidator;

    @Override
    public PostCreateResult createPost(PostCreateCommand command) {
        Post post = Post.builder()
            .authorId(command.getAuthorId())
            .title(command.getTitle())
            .content(command.getContent())
            .type(command.getType())
            .priority(command.getPriority())
            .build();

        Long postId = postWritePort.save(post);

        // ID가 설정된 Post 객체 재생성
        Post savedPost = Post.builder()
            .id(postId)
            .authorId(post.getAuthorId())
            .title(post.getTitle())
            .content(post.getContent())
            .type(post.getType())
            .status(post.getStatus())
            .priority(post.getPriority())
            .build();

        return PostCreateResult.toResult(savedPost);
    }

    @Override
    public PostUpdateResult updatePost(Long id, PostUpdateCommand command) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, command.getRequesterId());

        post.update(command);
        postWritePort.update(post);

        return PostUpdateResult.toResult(post);
    }

    @Override
    public PostDeleteResult softDeletePost(Long id, Long requesterId) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, requesterId);

        post.markDeleted();
        postWritePort.softDelete(id);

        return PostDeleteResult.toResult(post);
    }
}