package com.back2basics.domain.post.dto.response;

import com.back2basics.post.file.FileDownloadResult;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class FileDownloadResponse {

    private final String fileName;
    private final String fileType;
    private final String fileSize;
    private final String fileUrl;

    private FileDownloadResponse(String fileName, String fileType, String fileSize,
        String fileUrl) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
    }

    public static FileDownloadResponse toResponse(FileDownloadResult result) {
        return new FileDownloadResponse(
            result.fileName(),
            result.fileType(),
            result.fileSize(),
            result.fileUrl()
        );
    }

    public static List<FileDownloadResponse> toResponse(List<FileDownloadResult> results) {
        return results.stream()
            .map(FileDownloadResponse::toResponse)
            .collect(Collectors.toList());
    }
}