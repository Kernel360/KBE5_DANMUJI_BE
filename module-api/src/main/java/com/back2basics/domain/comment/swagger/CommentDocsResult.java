package com.back2basics.domain.comment.swagger;

public class CommentDocsResult {

    public static final String COMMENT_CREATE_REQUEST = """
        {
          "postId": 1,
          "parentId": null,
          "content": "그거 버그 아니고 기능인데요."
        }
        """;

    public static final String COMMENT_REPLY_CREATE_REQUEST = """
        {
          "postId": 1,
          "parentId": 1,
          "authorId": 2,
          "content": "@홍길동 확인했습니다!"
        }
        """;

    public static final String COMMENT_CREATE_SUCCESS = """
        {
          "success": true,
          "code": "C201",
          "message": "댓글 생성 완료",
          "data": 1
        }
        """;

    public static final String INVALID_INPUT = """
        {
          "success": false,
          "code": "C002",
          "message": "Invalid input type",
          "data": null
        }
        """;

    public static final String POST_NOT_FOUND = """
        {
          "success": false,
          "code": "P001",
          "message": "게시글을 찾을 수 없습니다",
          "data": null
        }
        """;

    public static final String COMMENT_READ_LIST_SUCCESS = """
        {
          "success": true,
          "code": "C202",
          "message": "댓글 목록 조회 완료",
          "data": [
            {
              "id": 1,
              "postId": 1,
              "authorId": 1,
              "content": "그거 버그 아니고 기능인데요.",
              "parentCommentId": null,
              "createdAt": "2024-01-01T10:00:00",
              "updatedAt": "2024-01-01T10:00:00",
              "childComments": [
                {
                  "id": 2,
                  "postId": 1,
                  "authorId": 2,
                  "content": "@홍길동 확인했습니다!",
                  "parentCommentId": 1,
                  "createdAt": "2024-01-01T10:05:00",
                  "updatedAt": "2024-01-01T10:05:00",
                  "childComments": []
                }
              ]
            }
          ]
        }
        """;

    public static final String COMMENT_UPDATE_REQUEST = """
        {
          "content": "수정된 댓글 내용입니다."
        }
        """;

    public static final String COMMENT_UPDATE_SUCCESS = """
        {
          "success": true,
          "code": "C203",
          "message": "댓글 수정 완료",
          "data": null
        }
        """;

    public static final String COMMENT_NOT_FOUND = """
        {
          "success": false,
          "code": "C001",
          "message": "댓글을 찾을 수 없습니다",
          "data": null
        }
        """;

    public static final String INVALID_COMMENT_AUTHOR = """
        {
          "success": false,
          "code": "C003",
          "message": "댓글 작성자가 아닙니다",
          "data": null
        }
        """;

    public static final String COMMENT_DELETE_SUCCESS = """
        {
          "success": true,
          "code": "C204",
          "message": "댓글 삭제 완료",
          "data": null
        }
        """;

    public static final String COMMENT_EMPTY_LIST = """
        {
          "success": true,
          "code": "C202",
          "message": "댓글 목록 조회 완료",
          "data": []
        }
        """;
}