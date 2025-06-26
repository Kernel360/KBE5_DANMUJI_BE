package com.back2basics.adapter.persistence.board.link.adapter;

import com.back2basics.adapter.persistence.board.link.LinkEntityRepository;
import com.back2basics.board.link.model.Link;
import com.back2basics.board.link.port.out.LinkDeletePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class LinkUpdateAdapter implements LinkDeletePort {

    private final LinkEntityRepository linkRepository;

    @Override
    @Transactional
    public void deleteLinks(List<Link> links) {
        List<Long> ids = links.stream()
            .map(Link::getId)
            .toList();
        linkRepository.deleteAllById(ids);
    }

    @Override
    @Transactional
    public void deleteByIds(List<Long> linkIds) {
        linkRepository.deleteAllById(linkIds);
    }
}