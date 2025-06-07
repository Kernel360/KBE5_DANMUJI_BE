package com.back2basics.domain.question.swagger;

public class QuestionDocsResult {

    public static final String QUESTION_READ_ONE_SUCCESS = """ 
        {
          "success": true,
          "code": "Q002",
          "message": "질문 조회 성공",
          "data": {
            "id": 1,
            "postId": 1,
            "author": {
              "id": 2,
              "username": "johndoe",
            },
            "content": "질문 내용입니다.",
            "status": "WAITING",
            "createdAt": "2024-01-01T10:00:00",
            "answers": [
              {
                "id": 100,
                "questionId": 1,
                "parentAnswerId": null,
                "author": {
                  "id": 3,
                  "username": "janedoe",
                },
                "content": "답변입니다.",
                "createdAt": "2024-01-01T11:00:00",
                "updatedAt": null,
                "children": []
              }
            ]
          }
        }
        """;
    public static final String QUESTION_CREATE_REQUEST = """
        {
          "postId": 1,
          "content": "이 게시글에 대해 질문이 있습니다."
        }
        """;

    public static final String QUESTION_CREATE_SUCCESS = """
        {
          "success": true,
          "code": "Q001",
          "message": "질문 생성 성공",
          "data": 1
        }
        """;

    public static final String QUESTION_READ_SUCCESS = """
        {
          "success": true,
          "code": "Q002",
          "message": "질문 조회 성공",
          "data": {
            "content": [
              {
                "id": 1,
                "postId": 1,
                "author": {
                  "id": 2,
                  "username": "johndoe",
                 },
                "content": "전체 질문 리스트입니다.",
                "status": "ANSWERED",
                "createdAt": "2024-01-01T12:00:00"
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

    public static final String QUESTION_LIST_READ_SUCCESS = """
        {
          "success": true,
          "code": "Q002",
          "message": "질문 조회 성공",
          "data": {
            "content": [
              {
                "id": 1,
                "postId": 1,
                "author": {
                  "id": 2,
                  "username": "johndoe",
                 },
                "content": "전체 질문 리스트입니다.",
                "status": "ANSWERED",
                "createdAt": "2024-01-01T12:00:00"
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

    public static final String INVALID_INPUT = """
        {
          "success": false,
          "code": "C002",
          "message": "Invalid input type",
          "data": null
        }
        """;

    public static final String QUESTION_UPDATE_REQUEST = """
        {
          "content": "수정된 질문 내용입니다."
        }
        """;

    public static final String QUESTION_UPDATE_SUCCESS = """
        {
          "success": true,
          "code": "Q003",
          "message": "질문 수정 성공",
          "data": null
        }
        """;

    public static final String QUESTION_DELETE_SUCCESS = """
        {
          "success": true,
          "code": "Q004",
          "message": "질문 삭제 성공",
          "data": null
        }
        """;

    public static final String QUESTION_MARK_ANSWERED_SUCCESS = """
        {
          "success": true,
          "code": "Q005",
          "message": "질문 상태 변경 성공 - 답변완료",
          "data": null
        }
        """;

    public static final String QUESTION_MARK_RESOLVED_SUCCESS = """
        {
          "success": true,
          "code": "Q006",
          "message": "질문 상태 변경 성공 - 해결됨",
          "data": null
        }
        """;

    public static final String INVALID_QUESTION_AUTHOR = """
        {
          "success": false,
          "code": "Q002",
          "message": "질문 작성자가 아닙니다",
          "data": null
        }
        """;

    public static final String QUESTION_NOT_FOUND = """
        {
          "success": false,
          "code": "Q001",
          "message": "질문을 찾을 수 없습니다",
          "data": null
        }
        """;
}