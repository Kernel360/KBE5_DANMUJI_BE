package com.back2basics.adapter.persistence.file;

import com.back2basics.file.model.ContentType;
import com.back2basics.file.model.File;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "files")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class FileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "Content_type")
    private ContentType contentType;

    @Column(name = "reference_id")
    private Long referenceId;

    @Column(name = "file_name")
    private String fileName;

    @Column(name = "file_url")
    private String fileUrl;

    @Column(name = "file_type") // 파일 확장자
    private String fileType;

    @Column(name = "file_size") // 파일 사이즈
    private String fileSize;

    public FileEntity(Long id, ContentType contentType, Long referenceId, String fileName,
        String fileUrl, String fileType,
        String fileSize) {
        this.id = id;
        this.contentType = contentType;
        this.referenceId = referenceId;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.fileType = fileType;
        this.fileSize = fileSize;

    }

    public static FileEntity of(File file) {
        return new FileEntity(
            file.getId(),
            file.getContentType(),
            file.getReferenceId(),
            file.getFileName(),
            file.getFileUrl(),
            file.getFileType(),
            file.getFileSize());
    }

}
