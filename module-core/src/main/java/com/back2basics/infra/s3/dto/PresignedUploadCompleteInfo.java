package com.back2basics.infra.s3.dto;

import lombok.Getter;

@Getter
public class PresignedUploadCompleteInfo {
    private String originalName;
    private String url;
    private String extension;
    private String size;

    public static PresignedUploadCompleteInfo of(String originalName, String url, String extension, String size) {
        PresignedUploadCompleteInfo info = new PresignedUploadCompleteInfo();
        info.originalName = originalName;
        info.url = url;
        info.extension = extension;
        info.size = size;
        return info;
    }
}