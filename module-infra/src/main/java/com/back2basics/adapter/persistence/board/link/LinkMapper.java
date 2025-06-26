package com.back2basics.adapter.persistence.board.link;


import com.back2basics.board.link.model.Link;
import org.springframework.stereotype.Component;

@Component
public class LinkMapper {

    public Link toDomain(LinkEntity entity) {
        return Link.create(
            entity.getId(),
            entity.getPostId(),
            entity.getUrl()
        );
    }

    public LinkEntity toEntity(Link link, Long postId) {
        return new LinkEntity(
            link.getId(),
            postId,
            link.getUrl()
        );
    }
}
