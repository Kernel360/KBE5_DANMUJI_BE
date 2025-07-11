package com.back2basics.board.link.service;

import com.back2basics.board.link.model.Link;
import com.back2basics.board.link.port.out.LinkDeletePort;
import com.back2basics.board.link.port.out.LinkReadPort;
import com.back2basics.board.link.port.out.LinkSavePort;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LinkUpdateService {

    private final LinkReadPort linkReadPort;
    private final LinkDeletePort linkDeletePort;
    private final LinkSavePort linkSavePort;

    public void updateLinks(List<String> newLinks, List<Long> linkIdsToDelete, Long postId) {
// 삭제
        if (linkIdsToDelete != null && !linkIdsToDelete.isEmpty()) {
            linkDeletePort.deleteByIds(linkIdsToDelete);
        }

        // 추가
        if (newLinks == null || newLinks.isEmpty()) {
            return;
        }

        List<Link> existingLinks = linkReadPort.getLinksByPostId(postId);
        Set<String> existingUrls = existingLinks.stream()
            .map(Link::getUrl)
            .collect(Collectors.toSet());

        List<Link> toAdd = newLinks.stream()
            .filter(url -> !existingUrls.contains(url))
            .map(url -> Link.create(null, postId, url))
            .toList();

        linkSavePort.saveAll(toAdd, postId);
    }

}