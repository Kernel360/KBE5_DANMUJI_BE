package com.back2basics.adapter.persistence.file.adapter;

import com.back2basics.file.port.out.FilePresignedUrlPort;
import com.back2basics.infra.s3.S3Util;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class S3PresignedUrlAdapter implements FilePresignedUrlPort {

    private final S3Util s3Util;

    @Override
    public String generateDownloadUrl(String fileKey) {
        return s3Util.generatePresignedDownloadUrl(fileKey);
    }
}
