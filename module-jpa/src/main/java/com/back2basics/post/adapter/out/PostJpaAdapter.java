package com.back2basics.post.adapter.out;


import com.back2basics.model.post.Post;
import com.back2basics.port.out.post.PostRepositoryPort;
import com.back2basics.post.entity.PostEntity;
import com.back2basics.post.mapper.PostMapper;
import com.back2basics.post.repository.PostEntityRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostJpaAdapter implements PostRepositoryPort {

    private final PostEntityRepository postRepository;
    private final PostMapper mapper;

    @Override
    public Long save(Post post) {
        PostEntity entity = mapper.fromDomain(post);
        return postRepository.save(entity).getId();
    }

    @Override
    public Optional<Post> findById(Long id) {
        return postRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<Post> findAll() {
        return postRepository.findAll().stream()
            .map(mapper::toDomain)
            .collect(Collectors.toList());
    }

    @Override
    public void update(Post post) {
        postRepository.save(mapper.fromDomain(post));
    }


    // todo : 고민해보기
    @Override
    public void softDelete(Post post) {
        PostEntity postEntity = mapper.fromDomain(post);
        postEntity.markDeleted();
        postRepository.save(mapper.fromDomain(post));

        // 모델 객체랑 엔티티 객체를 분리했던 이유는
        // 엔티티에 직접 접근해서 필드값 바꾸는 일 없도록 하기 위한 의도가 컸다.

        // softDelete도 어쨋든 update인데,(delete_at만 바꿈)
        // Post 모델 객체에서 변경된 값 들고와서 넣어주는 방식이 맞다고 생각했었음

        // 근데 BaseTimeEntity에서 deleted_at 이라는 필드를 공통적으로 사용해주고있기 때문에
        // 기존의 방법은 PostEntity에 delete_at이라는 필드를 또 중복으로 가져야만 모델에서 바뀐 값으로
        // mapper를 통해 entity로 바꿔줄 수 있었다.
        // 따라서 현재는 serviceImpl에서 Post.softDelete()를 삭제하고 jpaAdapter에서 entity에 접근하여
        // markDelete()를 호출하는 것으로 변경했다. -> 근데 이러면 본래의 의도가 깨지는 것 아닌지?

        // 그럼 두 갈래로 생각해볼 수 있다.
        // 모델 객체와 엔티티 객체의 분리에 중점을 두고 코드를 짤 것인지
        //    -> Post모델 객체에 updateDeleteAt() 이런걸 만들어서 변경된 값 자체를 어댑터에 넘겨주고 그걸 mapper로 엔티티변환 -> save
        // BaseTimeEntity를 만든 이유에 중점을 두고 코드를 짤 것인지
        //    -> serviceImpl에서는 단순히 adapter의 delete메소드를 타게 하고 나머지 로직은 서비스가 아니라 어댑터가 담당
        //    -> 어댑터 내부에서 Post모델 -> PostEntity 변환 해주고 entity의 필드값 직접 변경 후 save
    }
}