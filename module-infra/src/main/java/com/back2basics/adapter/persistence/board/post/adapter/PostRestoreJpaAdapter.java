package com.back2basics.adapter.persistence.board.post.adapter;

import static com.back2basics.infra.exception.post.PostErrorCode.POST_ALREADY_RESTORED;

import com.back2basics.adapter.persistence.board.post.PostEntity;
import com.back2basics.adapter.persistence.board.post.PostEntityRepository;
import com.back2basics.adapter.persistence.board.post.PostMapper;
import com.back2basics.board.post.model.Post;
import com.back2basics.board.post.port.out.PostRestorePort;
import com.back2basics.infra.exception.post.PostException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostRestoreJpaAdapter implements PostRestorePort {

    private final PostEntityRepository postRepository;
    private final PostReadJpaAdapter postReadJpaAdapter;
    private final PostMapper mapper;

    @Override
    public void restorePost(Post post) {
        Post deletedPost = postReadJpaAdapter.findDeletedPostById(post.getId())
            .orElseThrow(() -> new PostException(POST_ALREADY_RESTORED));

        PostEntity entity = mapper.toEntity(deletedPost);

        entity.restore();
        postRepository.save(entity);
    }
}
