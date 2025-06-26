package com.back2basics.adapter.persistence.board.link;


import com.back2basics.board.link.model.Link;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "links")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "post_id")
    private Long postId;

    @Column(name = "url")
    private String url;

    public LinkEntity(Long id, Long postId, String url) {
        this.id = id;
        this.postId = postId;
        this.url = url;
    }

    public static LinkEntity of(Link link) {
        return new LinkEntity(
            link.getId(),
            link.getPostId(),
            link.getUrl()
        );
    }
}