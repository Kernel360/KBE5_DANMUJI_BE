package com.back2basics.domain.company.swagger;

public class CompanyDocsResult {

    public static final String COMPANY_CREATE_REQUEST = """
        {
          "name" : "테스트",
          "ceoName" : "김철수",
          "bio" : "테스트용 회사입니다.",
          "bizNo" : 1234521345,
          "address" : "강남 미왕빌딩",
          "email" : "email@gmail.com",
          "tel" : "031-307-3323"
        }
        """;

    public static final String COMPANY_CREATE_SUCCESS = """
        {
            "status": "CREATED",
            "code": "COMP201",
            "message": "회사 생성 완료",
            "data": 1
        }
              
        """;

    public static final String INVALID_INPUT = """
        {
             "status": "BAD_REQUEST",
             "code": "C002",
             "message": "Invalid input type",
             "data": {
             }
         }
         
        """;

    public static final String COMPANY_READ_SUCCESS = """
        {
         "status": "OK",
         "code": "COMP202",
         "message": "회사 조회 완료",
         "data":{
         "id": 4,
         "name": "테스트",
         "ceoName": "김철수",
         "bio": "테스트용 회사입니다.",
         "bizNo": 1234521345,
         "address": "강남 미왕빌딩",
         "email": "email@gmail.com",
         "tel": "031-307-3323"
         }
         }
        """;

    public static final String COMPANY_NOT_FOUND = """
        {
         "status": "NOT_FOUND",
         "code": "COMP001",
         "message": "회사를 찾을 수 없습니다",
         "data":{}
         }
        """;

    public static final String COMPANY_READ_BY_NAME_ALL_SUCCESS = """
        {
             "status": "OK",
             "code": "COMP202",
             "message": "회사 조회 완료",
             "data": {
               "content": [
                 {
                   "id": 2,
                   "name": "test12345",
                   "ceoName": "test",
                   "bio": "test",
                   "bizNo": 1,
                   "address": "test",
                   "email": "email@gmail.com",
                   "tel": "031-307-3323"
                 },
                 {
                   "id": 3,
                   "name": "test12345",
                   "ceoName": "test",
                   "bio": "test",
                   "bizNo": 123,
                   "address": "test",
                   "email": "email@gmail.com",
                   "tel": "031-307-3323"
                 }
               ],
               "page": {
                 "size": 10,
                 "number": 0,
                 "totalElements": 2,
                 "totalPages": 1
               }
             }
           }
        """;

    public static final String COMPANY_READ_ALL_SUCCESS = """
        {
            "status": "OK",
            "code": "COMP203",
            "message": "회사 목록 조회 완료",
            "data": {
              "content": [
                {
                  "id": 2,
                  "name": "test12345",
                  "ceoName": "test",
                  "bio": "test",
                  "bizNo": 1,
                  "address": "test",
                  "email": "email@gmail.com",
                  "tel": "031-307-3323"
                },
                {
                  "id": 3,
                  "name": "test12345",
                  "ceoName": "test",
                  "bio": "test",
                  "bizNo": 123,
                  "address": "test",
                  "email": "email@gmail.com",
                  "tel": "031-307-3323"
                },
                {
                  "id": 4,
                  "name": "테스트",
                  "ceoName": "김철수",
                  "bio": "테스트용 회사입니다.",
                  "bizNo": 1234521345,
                  "address": "강남 미왕빌딩",
                  "email": "email@gmail.com",
                  "tel": "031-307-3323"
                }
              ],
              "page": {
                "size": 10,
                "number": 0,
                "totalElements": 3,
                "totalPages": 1
              }
            }
          }
        """;

    public static final String CREATE_UPDATE_REQUEST = """
        {
          "requesterId": 1,
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

    public static final String COMPANY_DELETE_SUCCESS = """
        {
          "status": "OK",
          "code": "COMP205",
          "message": "회사 삭제 완료"
        }
        """;

    public static final String COMPANY_UPDATE_REQUEST = """
        {
          "name" : "테스트회사 수정",
          "ceoName" : "김국수",
          "bio" : "테스트용 회사 수정합니다.",
          "bizNo" : 12321,
          "address" : "강남역 4번출구 미왕빌딩",
          "email" : "test@naver.com",
          "tel" : "031-333-3300"
        }
        """;

    public static final String COMPANY_UPDATE_SUCCESS = """
        {
            "status": "OK",
            "code": "COMP204",
            "message": "회사 수정 완료",
            "data": 1
        }
                
        """;
}
