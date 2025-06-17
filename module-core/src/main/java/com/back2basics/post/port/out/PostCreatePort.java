package com.back2basics.post.port.out;

import com.back2basics.post.file.File;
import com.back2basics.post.model.Post;
import java.util.List;

public interface PostCreatePort {

    void save(Post post, List<File> files);
}
