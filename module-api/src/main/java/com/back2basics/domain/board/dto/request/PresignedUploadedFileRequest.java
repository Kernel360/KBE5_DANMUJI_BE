package com.back2basics.domain.board.dto.request;

import lombok.Getter;

@Getter
public class PresignedUploadedFileRequest {
    private String originalName;
    private String url;
    private String extension;
    private String size;
}
