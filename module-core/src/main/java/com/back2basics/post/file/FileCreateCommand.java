package com.back2basics.post.file;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class FileCreateCommand {

    private String fileName;
    private String fileUrl;
    private String fileType;
    private String fileSize;
}
