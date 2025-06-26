package com.back2basics.board.link.port.out;

import com.back2basics.board.link.model.Link;
import java.util.List;

public interface LinkDeletePort {

    void deleteLinks(List<Link> links);
}
