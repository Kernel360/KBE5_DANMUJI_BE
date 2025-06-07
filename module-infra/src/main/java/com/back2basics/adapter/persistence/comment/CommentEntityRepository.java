package com.back2basics.adapter.persistence.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentEntityRepository extends JpaRepository<CommentEntity, Long> {

//    /*
//        select *
//        from comment c
//            fetch join post
//        where c.post_id = :postId
//     */
//    @Query("SELECT c FROM CommentEntity c WHERE c.post.id = :postId")
//    List<CommentEntity> findAllCommentsByPostId(Long postId);

}
