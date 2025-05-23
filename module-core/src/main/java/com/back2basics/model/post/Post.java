package com.back2basics.model.post;

import com.back2basics.service.post.exception.PostErrorCode;
import com.back2basics.service.post.exception.PostException;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

    private final Long id;
    private String authorName;
    private String title;
    private String content;

    @Builder
    public Post(Long id, String authorName, String title, String content) {
        this.id = id;
        this.authorName = authorName;
        this.title = title;
        this.content = content;
    }

    public void update(String title, String content) {
        if(title != null){
            this.title = title;
        }
        if(content != null){
            this.content = content;
        }
    }
}