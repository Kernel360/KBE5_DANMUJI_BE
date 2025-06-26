package com.back2basics.adapter.persistence.board.link.adapter;


import com.back2basics.adapter.persistence.board.link.LinkEntity;
import com.back2basics.adapter.persistence.board.link.LinkEntityRepository;
import com.back2basics.board.link.model.Link;
import com.back2basics.board.link.port.out.LinkSavePort;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LinkCreateAdapter implements LinkSavePort {

    private final LinkEntityRepository linkRepository;

    @Override
    public void saveAll(List<Link> links, Long postId) {
        List<LinkEntity> entities = links.stream()
            .map(LinkEntity::of)
            .collect(Collectors.toList());
        linkRepository.saveAll(entities);
    }
}