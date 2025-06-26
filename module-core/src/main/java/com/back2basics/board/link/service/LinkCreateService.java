package com.back2basics.board.link.service;


import com.back2basics.board.link.model.Link;
import com.back2basics.board.link.port.out.LinkSavePort;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkCreateService {

    private final LinkSavePort linkSavePort;

    public void createLinks(List<String> newLinks, Long postId) {
        List<Link> links = newLinks.stream()
            .map(url -> Link.create(null, postId, url))
            .toList();

        linkSavePort.saveAll(links, postId);
    }
}