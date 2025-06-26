package com.back2basics.board.link.model;

import lombok.Getter;

@Getter
public class Link {

    private final Long id;
    private Long postId;
    private String url;

    public Link(Long id, Long postId, String url) {
        this.id = id;
        this.postId = postId;
        this.url = url;
    }

    public static Link create(Long id, Long postId, String url) {
        return new Link(id, postId, url);
    }

    public void update(String url) {
        this.url = url;
    }
}