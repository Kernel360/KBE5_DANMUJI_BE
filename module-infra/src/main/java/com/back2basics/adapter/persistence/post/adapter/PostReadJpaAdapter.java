package com.back2basics.adapter.persistence.post.adapter;

import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostReadPort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostReadJpaAdapter implements PostReadPort {


    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public Page<Post> findAllWithPaging(Pageable pageable) {

        // id 페이징
        List<Long> ids = postRepository.findActivePostIds(pageable);

        // fetch join 쿼리
        List<Post> posts = postRepository.findActivePostsWithCommentsByIds(ids).stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());

        // 카운트 쿼리
        long total = postRepository.countActivePosts();
        return new PageImpl<>(posts, pageable, total);
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id).map(mapper::toDomain);
    }

}
