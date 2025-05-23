package com.back2basics.model.post;

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

	// todo : Post 관련 CustomException 추가 필요
	public void isAuthor(String requesterName) {
		if (!this.authorName.equals(requesterName)) {
			throw new IllegalStateException("작성자만 수정 또는 삭제할 수 있습니다.");
		}
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