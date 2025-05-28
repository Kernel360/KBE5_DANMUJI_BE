package com.back2basics.post.service;

import com.back2basics.infra.validation.validator.PostValidator;
import com.back2basics.post.model.Post;
import com.back2basics.post.model.PostStatus;
import com.back2basics.post.port.in.CreatePostUseCase;
import com.back2basics.post.port.in.DeletePostUseCase;
import com.back2basics.post.port.in.GetPostUseCase;
import com.back2basics.post.port.in.UpdatePostUseCase;
import com.back2basics.post.port.in.command.PostCreateCommand;
import com.back2basics.post.port.in.command.PostUpdateCommand;
import com.back2basics.post.port.out.PostCreatePort;
import com.back2basics.post.port.out.PostReadPort;
import com.back2basics.post.port.out.PostSoftDeletePort;
import com.back2basics.post.port.out.PostUpdatePort;
import com.back2basics.post.service.result.PostInfoResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements // todo : 각 CRUD 기능 별 뭘 리턴할지 정해봐야할듯
    CreatePostUseCase,
    GetPostUseCase,
    UpdatePostUseCase,
    DeletePostUseCase {

    private final PostCreatePort postCreatePort;
    private final PostReadPort postReadPort;
    private final PostUpdatePort postUpdatePort;
    private final PostSoftDeletePort postSoftDeletePort;
    private final PostValidator postValidator;

    @Override
    public Long createPost(PostCreateCommand command) {
        Post post = Post.builder()
            .authorName(command.getAuthorName())
            .title(command.getTitle())
            .content(command.getContent())
            .type(command.getType())
            .priority(command.getPriority())
            .status(PostStatus.PENDING)
            .completedAt(null)
            .deletedAt(null)
            .build();
        return postCreatePort.save(post);
    }

    @Override
    public PostInfoResult getPost(Long id) {
        Post post = postValidator.findPost(id);
        return PostInfoResult.from(post);
    }

    @Override
    public List<PostInfoResult> getAllPosts() {
        return postReadPort.findAll().stream()
            .map(PostInfoResult::from)
            .collect(Collectors.toList());
    }

    @Override // todo : requesterName은 시큐리티 연결되면 컨트롤러에서 파라미터로 넘겨주는 걸로? 현재는 updateCommand에 일단 넣어서 사용
    public void updatePost(Long id, PostUpdateCommand command) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, command.getRequesterName());

        post.update(command);
        postUpdatePort.update(post);
    }

    @Override
    public void softDeletePost(Long id, String requesterName) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, requesterName);
        post.markDeleted();
        postSoftDeletePort.softDelete(post);
    }
}