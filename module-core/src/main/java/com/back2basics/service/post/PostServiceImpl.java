package com.back2basics.service.post;

import com.back2basics.model.post.Post;
import com.back2basics.port.in.post.CreatePostUseCase;
import com.back2basics.port.in.post.DeletePostUseCase;
import com.back2basics.port.in.post.GetPostUseCase;
import com.back2basics.port.in.post.UpdatePostUseCase;
import com.back2basics.port.out.post.PostRepositoryPort;
import com.back2basics.service.post.dto.PostCreateCommand;
import com.back2basics.service.post.dto.PostResponseDto;
import com.back2basics.service.post.dto.PostUpdateCommand;
import com.back2basics.service.post.exception.PostErrorCode;
import com.back2basics.service.post.exception.PostException;
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

    @Override
    public Long createPost(PostCreateCommand command) {
        Post post = Post.builder()
            .authorName(command.getAuthorName())
            .title(command.getTitle())
            .content(command.getContent())
            .build();
        return postRepository.save(post);
    }

    @Override // todo : validator 추가 예정
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostException(
                PostErrorCode.POST_NOT_FOUND));
        return PostResponseDto.from(post);
    }

    @Override
    public List<PostResponseDto> getAllPosts() {
        return postRepository.findAll().stream()
            .map(PostResponseDto::from)
            .collect(Collectors.toList());
    }

    @Override // todo : 작성자 검증 필요
    public void updatePost(Long id, PostUpdateCommand command) {
        Post post = postRepository.findById(id)
            .orElseThrow(() -> new PostException(
                PostErrorCode.POST_NOT_FOUND));
        post.update(command.getTitle(), command.getContent());
        postRepository.update(post);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}