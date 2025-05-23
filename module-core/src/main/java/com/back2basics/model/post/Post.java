package com.back2basics.model.post;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
public class Post {

	private final Long id;
	private String authorName;
	private String title;
	private String content;
	private final LocalDateTime createdAt;
	private LocalDateTime updatedAt;

	@Builder
	public Post(Long id, String authorName, String title, String content, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
		this.id = id;
		this.authorName = authorName;
		this.title = title;
		this.content = content;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public void update(String title, String content) {
		if (title != null) {
			this.title = title;
		}
		if (content != null) {
			this.content = content;
		}
	}
}