package com.ex01.basic.repository.post;


import com.ex01.basic.entity.post.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Long> {
}
