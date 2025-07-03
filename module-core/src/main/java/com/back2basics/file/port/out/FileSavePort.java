package com.back2basics.file.port.out;

import com.back2basics.file.model.File;
import java.util.List;

public interface FileSavePort {

    void saveAll(List<File> files, Long referenceId);
}