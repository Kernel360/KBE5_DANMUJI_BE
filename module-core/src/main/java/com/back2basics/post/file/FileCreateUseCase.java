package com.back2basics.post.file;

public interface FileCreateUseCase {

    FileCreateResult createFile(FileCreateCommand command);
}