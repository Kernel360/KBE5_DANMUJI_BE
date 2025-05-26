package com.back2basics.service.post;

import com.back2basics.infra.validation.PostValidator;
import com.back2basics.model.post.Post;
import com.back2basics.model.post.PostStatus;
import com.back2basics.port.in.post.CreatePostUseCase;
import com.back2basics.port.in.post.DeletePostUseCase;
import com.back2basics.port.in.post.GetPostUseCase;
import com.back2basics.port.in.post.UpdatePostUseCase;
import com.back2basics.port.out.post.PostRepositoryPort;
import com.back2basics.service.post.dto.PostCreateCommand;
import com.back2basics.service.post.dto.PostResponseDto;
import com.back2basics.service.post.dto.PostUpdateCommand;
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

    private final PostRepositoryPort postRepository;
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
        return postRepository.save(post);
    }

    @Override
    public PostResponseDto getPost(Long id) {
        Post post = postValidator.findPost(id);
        return PostResponseDto.from(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
            .map(PostResponseDto::from)
            .collect(Collectors.toList());
    }

    @Override // todo : requesterName은 시큐리티 연결되면 컨트롤러에서 파라미터로 넘겨주는 걸로? 현재는 updateCommand에 일단 넣어서 사용
    public void updatePost(Long id, PostUpdateCommand command) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, command.getRequesterName());

        post.update(command);
        postRepository.update(post);
    }

    @Override
    public void softDeletePost(Long id, String requesterName) {
        Post post = postValidator.findPost(id);
        postValidator.isAuthor(post, requesterName);
        //post.softDelete();
        postRepository.softDelete(post);
    }
}