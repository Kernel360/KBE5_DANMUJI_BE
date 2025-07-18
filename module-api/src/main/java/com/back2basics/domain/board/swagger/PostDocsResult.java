package com.back2basics.domain.board.swagger;

public class PostDocsResult {

    public static final String POST_CREATE_REQUEST = """
        {
          "title": "새로운 게시글 제목",
          "content": "게시글 내용입니다.",
          "type": "GENERAL",
          "priority": "HIGH",
          "projectId":1,
          "stepId": 1
        }
        """;

    public static final String POST_CREATE_SUCCESS = """
        {
          "success": true,
          "code": "P201",
          "message": "게시글 생성 완료",
          "data": {
            "id": 1,
            "title": "새로운 게시글 제목"
          }
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

    public static final String POST_READ_SUCCESS = """
        {
          "success": true,
          "code": "P202",
          "message": "게시글 조회 완료",
          "data": {
            "postId": 1,
            "author": {
              "id": 1,
              "name": "홍길동"
            },
            "projectStepId": 1,
            "title": "게시글 제목",
            "content": "게시글 내용",
            "type": "GENERAL",
            "status": "PENDING",
            "priority": 1,
            "createdAt": "2024-01-01T10:00:00",
            "updatedAt": "2024-01-01T10:00:00",
            "deletedAt": null,
            "completedAt": null,
            "isDelete": false
          }
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

    public static final String POST_READ_ALL_SUCCESS = """
        {
          "success": true,
          "code": "P203",
          "message": "게시글 목록 조회 완료",
          "data": {
            "content": [
              {
                "postId": 1,
                "author": {
                  "id": 1,
                  "name": "홍길동"
                },
                "projectStepId": 1,
                "title": "첫 번째 게시글",
                "content": "게시글 내용입니다",
                "type": "GENERAL",
                "status": "PENDING",
                "priority": 1,
                "createdAt": "2024-01-01T10:00:00",
                "updatedAt": "2024-01-01T10:00:00",
                "deletedAt": null,
                "completedAt": null,
                "isDelete": false
              }
            ],
            "pageable": {
              "pageNumber": 0,
              "pageSize": 10
            },
            "totalElements": 1,
            "totalPages": 1
          }
        }
        """;

    public static final String POST_UPDATE_REQUEST = """
        {
          "title": "수정된 게시글 제목",
          "content": "수정된 게시글 내용입니다.",
          "type": "NOTICE",
          "status": "PENDING",
          "priority": 2
        }
        """;

    public static final String POST_UPDATE_SUCCESS = """
        {
          "success": true,
          "code": "P204",
          "message": "게시글 수정 완료",
          "data": null
        }
        """;

    public static final String INVALID_POST_AUTHOR = """
        {
          "success": false,
          "code": "P002",
          "message": "게시글 작성자가 아닙니다",
          "data": null
        }
        """;

    public static final String POST_DELETE_SUCCESS = """
        {
          "success": true,
          "code": "P205",
          "message": "게시글 삭제 완료",
          "data": null
        }
        """;

    public static final String POST_SEARCH_SUCCESS = """
        {
          "success": true,
          "code": "P203",
          "message": "게시글 목록 조회 완료",
          "data": {
            "content": [
              {
                "postId": 1,
                "author": {
                  "id": 1,
                  "name": "홍길동",
                  "email": "hong@test.com"
                },
                "projectStepId": 1,
                "title": "첫 번째 게시글",
                "type": "GENERAL",
                "status": "PENDING",
                "priority": 1,
                "createdAt": "2024-01-01T10:00:00",
                "deletedAt": null,
                "completedAt": null,
                "isDelete": false
              }
            ],
            "pageable": {
              "pageNumber": 0,
              "pageSize": 10
            },
            "totalElements": 1,
            "totalPages": 1
          }
        }
        """;
}