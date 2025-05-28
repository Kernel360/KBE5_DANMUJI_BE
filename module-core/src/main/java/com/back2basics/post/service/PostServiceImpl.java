package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.port.in.CreatePostUseCase;
import com.back2basics.post.port.in.DeletePostUseCase;
import com.back2basics.post.port.in.GetPostDetailsUseCase;
import com.back2basics.post.port.in.GetPostListUseCase;
import com.back2basics.post.port.in.UpdatePostUseCase;
import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import com.back2basics.post.port.out.PostCreatePort;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.port.out.PostSoftDeletePort;
import com.back2basics.post.port.out.PostUpdatePort;
import com.back2basics.post.service.result.PostCreateResult;
import com.back2basics.post.service.result.PostDetailsResult;
import com.back2basics.post.service.result.PostSimpleResult;
import com.back2basics.post.service.result.PostUpdateResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements // todo : 각 CRUD 기능 별 뭘 리턴할지 정해봐야할듯
    CreatePostUseCase,
    GetPostDetailsUseCase,
    GetPostListUseCase,
    UpdatePostUseCase,
    DeletePostUseCase {

    private final PostCreatePort postCreatePort;
    private final PostReadPort postReadPort;
    private final PostUpdatePort postUpdatePort;
    private final PostSoftDeletePort postSoftDeletePort;
    private final PostValidator postValidator;

    @Override
    public PostCreateResult createPost(PostCreateCommand command) {
        Post post = Post.builder()
            .authorId(command.getAuthorId())
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

    @Override
    public PostDetailsResult getPost(Long id) {
        Post post = postValidator.findPost(id);
        return PostDetailsResult.toResult(post);
    }

    @Override
    public List<PostSimpleResult> getPostList() {
        return postReadPort.findAll().stream()
            .map(PostSimpleResult::toResult)
            .collect(Collectors.toList());
    }

    @Override // todo : requesterName은 시큐리티 연결되면 컨트롤러에서 파라미터로 넘겨주는 걸로? 현재는 updateCommand에 일단 넣어서 사용
    public PostUpdateResult updatePost(Long id, PostUpdateCommand command) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, command.getRequesterId());

        post.update(command);

        postUpdatePort.update(post);
        return PostUpdateResult.toResult(post);
    }

    @Override
    public void softDeletePost(Long id, Long requesterId) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, requesterId);
        post.markDeleted();
        postSoftDeletePort.softDelete(post);
    }
}