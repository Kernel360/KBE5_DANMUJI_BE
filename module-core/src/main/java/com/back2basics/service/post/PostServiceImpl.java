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
public class PostServiceImpl implements
	CreatePostUseCase,
	GetPostUseCase,
	UpdatePostUseCase,
	DeletePostUseCase { // todo : 응답 데이터 통일 필요. 현재 어떤건 id, 어떤건 post

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

	@Override
	public PostResponseDto getPost(Long id) {
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostException(
				PostErrorCode.POST_NOT_FOUND)); // todo : valdator 추가해서 여기서 throw 안하게 수정 필요
		return PostResponseDto.from(post);
	}

	@Override
	public List<PostResponseDto> getAllPosts() {
		return postRepository.findAll().stream()
			.map(PostResponseDto::from)
			.collect(Collectors.toList());
	}

	@Override
	public void updatePost(Long id, PostUpdateCommand command) {
		// todo : 작성자 검증 필요
		Post post = postRepository.findById(id)
			.orElseThrow(() -> new PostException(
				PostErrorCode.POST_NOT_FOUND)); // todo : valdator 추가해서 여기서 throw 안하게 수정 필요
		post.update(command.getTitle(), command.getContent());
		postRepository.update(post);
	}

	@Override
	public void deletePost(Long id) {
		postRepository.deleteById(id);
	}
}