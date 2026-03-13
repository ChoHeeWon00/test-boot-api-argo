package com.ex01.basic.repository.post;

import com.ex01.basic.entity.post.PostCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostCountRepository extends JpaRepository<PostCountEntity, Long> {
    boolean existsByMemberEntity_IdAndPostEntity_Id(int memberId, Long postId);
    long countByPostEntity_Id(Long postId);
}
