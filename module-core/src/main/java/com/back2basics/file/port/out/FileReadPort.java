package com.back2basics.file.port.out;

import com.back2basics.file.model.File;
import java.util.List;

public interface FileReadPort {

    List<File> getFilesByReferenceId(Long referenceId);

    File getFileById(Long fileId);
}