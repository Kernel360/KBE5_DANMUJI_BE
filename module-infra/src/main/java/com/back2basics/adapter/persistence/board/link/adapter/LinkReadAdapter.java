package com.back2basics.adapter.persistence.board.link.adapter;

import com.back2basics.adapter.persistence.board.link.LinkEntityRepository;
import com.back2basics.adapter.persistence.board.link.LinkMapper;
import com.back2basics.board.link.model.Link;
import com.back2basics.board.link.port.out.LinkReadPort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkReadAdapter implements LinkReadPort {

    private final LinkEntityRepository linkRepository;
    private final LinkMapper linkMapper;

    @Override
    public List<Link> getLinksByPostId(Long postId) {
        return linkRepository.findAllByPostId(postId).stream()
            .map(linkMapper::toDomain)
            .toList();
    }
}