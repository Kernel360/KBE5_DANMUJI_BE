package com.back2basics.response.company;

import com.back2basics.response.global.result.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CompanyResponseCode implements ResponseCode {

  COMPANY_CREATE_SUCCESS(201, "COMP201", "회사 생성 완료"),
  COMPANY_READ_SUCCESS(200, "COMP202", "회사 조회 완료"),
  COMPANY_READ_ALL_SUCCESS(200, "COMP203", "회사 목록 조회 완료"),
  COMPANY_UPDATE_SUCCESS(200, "COMP204", "회사 수정 완료"),
  COMPANY_DELETE_SUCCESS(200, "COMP205", "회사 삭제 완료");

  private final int status;
  private final String code;
  private final String message;

}
