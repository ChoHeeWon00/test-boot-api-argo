package com.ex01.basic.repository.post;

import com.ex01.basic.entity.post.PostCountEntity;
import com.ex01.basic.entity.post.PostLikeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostLikeRepository extends JpaRepository<PostLikeEntity, Long> {
    boolean existsByMemberEntity_IdAndPostEntity_Id(int memberId, Long postId);
    long countByPostEntity_Id(Long postId);
    void deleteByMemberEntity_IdAndPostEntity_Id(int memberId, Long postId);
}
