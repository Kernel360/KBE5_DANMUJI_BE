//package com.back2basics.domain.answer.swagger;
//
//public class AnswerDocsResult {
//
//    public static final String ANSWER_CREATE_SUCCESS = """
//        {
//          "success": true,
//          "code": "AN201",
//          "message": "답변 생성 완료",
//          "data": 1
//        }
//        """;
//
//    public static final String ANSWER_UPDATE_SUCCESS = """
//        {
//          "success": true,
//          "code": "AN203",
//          "message": "답변 수정 완료",
//          "data": null
//        }
//        """;
//
//    public static final String ANSWER_DELETE_SUCCESS = """
//        {
//          "success": true,
//          "code": "AN202",
//          "message": "답변 삭제 완료",
//          "data": null
//        }
//        """;
//
//    public static final String ANSWER_CREATE_REQUEST = """
//        {
//          "questionId": 1,
//          "parentId": null,
//          "content": "이것은 새로운 답변입니다."
//        }
//        """;
//
//    public static final String ANSWER_UPDATE_REQUEST = """
//        {
//          "content": "수정된 답변 내용입니다."
//        }
//        """;
//
//    public static final String ANSWER_READ_SUCCESS = """
//        {
//          "success": true,
//          "code": "AN204",
//          "message": "답변 조회 완료",
//          "data": [
//            {
//              "id": 100,
//              "questionId": 1,
//              "parentId": null,
//              "authorIp": "192.168.0.1",
//              "author": {
//                "id": 3,
//                "name": "홍길동"
//              },
//              "content": "답변입니다.",
//              "createdAt": "2024-01-01T11:00:00",
//              "updatedAt": null,
//              "children": []
//            }
//          ]
//        }
//        """;
//
//}
