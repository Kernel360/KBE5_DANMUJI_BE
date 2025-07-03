package com.back2basics.file.port.out;

import com.back2basics.file.model.File;
import java.util.List;

public interface FileDeletePort {

    void deleteAllByReferenceId(Long referenceId);

    void deleteFiles(List<File> files);

    void deleteFromStorage(List<File> fileUrls);

}