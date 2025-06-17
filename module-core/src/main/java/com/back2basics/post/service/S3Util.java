package com.back2basics.post.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
@RequiredArgsConstructor
public class S3Util {

    private final S3Client s3Client;

    @Value("${cloudflare.r2.bucket}")
    private String bucket;

    private static final String PUBLIC_URL = "https://pub-2823e9b2fef94861b9d9cc553a84821b.r2.dev/";

    /**
     * 단일 파일 업로드
     */
    public String uploadFile(String key, MultipartFile multipartFile) throws IOException {
        s3Client.putObject(
            PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(multipartFile.getContentType()) // MIME type 자동 감지
                .build(),
            RequestBody.fromInputStream(multipartFile.getInputStream(), multipartFile.getSize())
        );
        return PUBLIC_URL + key;
    }

    /**
     * 여러 파일 업로드
     */
    public List<String> uploadFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<String> fileUrls = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            String key = UUID.randomUUID() + "_" + file.getOriginalFilename();
            fileUrls.add(uploadFile(key, file));
        }
        return fileUrls;
    }

    /**
     * 파일 다운로드
     */
    public byte[] downloadFile(String key) throws IOException {
        return s3Client.getObject(
            GetObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build()
        ).readAllBytes();
    }

    /**
     * 파일 삭제
     */
    public void deleteFile(String key) {
        s3Client.deleteObject(
            DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build()
        );
    }
}
