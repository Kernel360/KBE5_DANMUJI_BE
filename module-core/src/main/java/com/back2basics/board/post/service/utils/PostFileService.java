package com.back2basics.board.post.service.utils;

import com.back2basics.file.model.ContentType;
import com.back2basics.file.model.File;
import com.back2basics.file.port.out.FileDeletePort;
import com.back2basics.file.port.out.FileReadPort;
import com.back2basics.file.port.out.FileSavePort;
import com.back2basics.file.service.FileUploadService;
import com.back2basics.infra.s3.dto.PresignedUploadCompleteInfo;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
@RequiredArgsConstructor
public class PostFileService {

    private final FileUploadService fileUploadService;
    private final FileSavePort fileSavePort;
    private final FileDeletePort fileDeletePort;
    private final FileReadPort fileReadPort;


    public void uploadAndSave(List<MultipartFile> files, Long postId) throws IOException {
        if (files == null || files.isEmpty()) return;
        List<File> fileModels = fileUploadService.upload(files, postId, ContentType.POST);
        fileSavePort.saveAll(fileModels, postId);
    }

    public void savePresigned(List<PresignedUploadCompleteInfo> uploadedFiles, Long postId) {
        if (uploadedFiles == null || uploadedFiles.isEmpty()) return;
        List<File> fileModels = uploadedFiles.stream()
            .map(info -> File.create(
                null, ContentType.POST, postId,
                info.getOriginalName(), info.getUrl(),
                info.getExtension(), info.getSize()
            )).toList();
        fileSavePort.saveAll(fileModels, postId);
    }

    public void replaceFiles(List<MultipartFile> files, List<Long> fileIdsToDelete, Long postId) throws IOException {
        List<File> toDelete = getFilesToDelete(fileIdsToDelete, postId);
        fileDeletePort.deleteFiles(toDelete);
        fileDeletePort.deleteFromStorage(toDelete);

        if (files != null && !files.isEmpty()) {
            List<File> newFiles = fileUploadService.upload(files, postId, ContentType.POST);
            fileSavePort.saveAll(newFiles, postId);
        }
    }

    public void replacePresignedFiles(List<PresignedUploadCompleteInfo> uploadedFiles, List<Long> fileIdsToDelete, Long postId) {
        List<File> toDelete = getFilesToDelete(fileIdsToDelete, postId);
        fileDeletePort.deleteFiles(toDelete);
        fileDeletePort.deleteFromStorage(toDelete);

        if (uploadedFiles != null && !uploadedFiles.isEmpty()) {
            List<File> newFiles = uploadedFiles.stream()
                .map(info -> File.create(
                    null, ContentType.POST, postId,
                    info.getOriginalName(), info.getUrl(),
                    info.getExtension(), info.getSize()
                )).toList();
            fileSavePort.saveAll(newFiles, postId);
        }
    }

    private List<File> getFilesToDelete(List<Long> fileIdsToDelete, Long postId) {
        if (fileIdsToDelete == null || fileIdsToDelete.isEmpty()) return List.of();
        List<File> existingFiles = fileReadPort.getFilesByReferenceId(postId);
        return existingFiles.stream()
            .filter(f -> fileIdsToDelete.contains(f.getId()))
            .toList();
    }
}