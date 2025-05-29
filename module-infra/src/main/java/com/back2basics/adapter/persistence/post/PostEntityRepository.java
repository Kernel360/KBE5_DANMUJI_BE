package com.back2basics.adapter.persistence.post;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostEntityRepository extends JpaRepository<PostEntity, Long> {

    // 전체 게시글 페이지네이션(삭제안된애들 최신순)
    // 1. id로 페이징 쿼리
    /*
        select p.id
        from post p
        where p.deleted_at is null
        order by p.created_at desc, p.id desc
     */
    @Query("SELECT p.id FROM PostEntity p WHERE p.deletedAt IS NULL ORDER BY p.createdAt DESC, p.id DESC")
    List<Long> findActivePostIds(Pageable pageable);

    // 2. id로 연관 엔티티 조인(in쿼리 : fetch join)
    /*
        select p.*, c.*
        from post p
        left join comment c on c.post_id = p.id
        where p.deleted_at is null
        order by p.created_at desc, p.id desc
     */
    @Query("SELECT DISTINCT p FROM PostEntity p " +
        "LEFT JOIN FETCH p.comments c " +
        "WHERE p.id IN :postIds AND p.deletedAt IS NULL " +
        "ORDER BY p.createdAt DESC, p.id DESC")
    List<PostEntity> findActivePostsWithCommentsByIds(List<Long> postIds);

    // 3. 카운트 쿼리(total)
    /*
        select count(p)
        from post p
        where p.deleted_at is null
     */
    @Query("SELECT COUNT(p) FROM PostEntity p WHERE p.deletedAt IS NULL")
    long countActivePosts();

}
