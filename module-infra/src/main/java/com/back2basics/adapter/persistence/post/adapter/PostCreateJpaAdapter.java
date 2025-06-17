package com.back2basics.adapter.persistence.post.adapter;

import com.back2basics.adapter.persistence.post.PostEntity;
import com.back2basics.adapter.persistence.post.PostEntityRepository;
import com.back2basics.adapter.persistence.post.PostMapper;
import com.back2basics.adapter.persistence.post.file.FileEntity;
import com.back2basics.adapter.persistence.post.file.FileEntityRepository;
import com.back2basics.adapter.persistence.post.file.FileMapper;
import com.back2basics.post.file.File;
import com.back2basics.post.model.Post;
import com.back2basics.post.port.out.PostCreatePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostCreateJpaAdapter implements PostCreatePort {

    private final PostEntityRepository postRepository;
    private final PostMapper postMapper;
    private final FileEntityRepository fileRepository;
    private final FileMapper fileMapper;

    @Override
    public void save(Post post, List<File> files) {
        PostEntity postEntity = postMapper.toEntity(post);
        PostEntity savedPost = postRepository.save(postEntity);

        if (files != null && !files.isEmpty()) {
            List<FileEntity> fileEntities = files.stream()
                .map(file -> fileMapper.toEntity(file, savedPost.getId()))
                .toList();
            fileRepository.saveAll(fileEntities);
        }
    }
}
