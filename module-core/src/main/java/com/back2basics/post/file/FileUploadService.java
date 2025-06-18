package com.back2basics.post.file;

import com.back2basics.infra.s3.S3Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class FileUploadService {

    private final S3Util s3Util;

    public List<File> upload(List<MultipartFile> multipartFiles, Long postId) throws IOException {
        List<File> fileModels = new ArrayList<>();

        if (multipartFiles == null || multipartFiles.isEmpty()) {
            return fileModels;
        }

        List<String> uploadedUrls = s3Util.uploadFiles(multipartFiles);
        for (int i = 0; i < multipartFiles.size(); i++) {
            MultipartFile multipartFile = multipartFiles.get(i);
            String fileUrl = uploadedUrls.get(i);

            fileModels.add(File.create(
                null,
                postId,
                multipartFile.getOriginalFilename(),
                fileUrl,
                extractExtension(multipartFile.getOriginalFilename()),
                String.valueOf(multipartFile.getSize())
            ));
        }

        return fileModels;
    }

    private String extractExtension(String fileName) {
        if (fileName == null || !fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf('.') + 1);
    }
}
