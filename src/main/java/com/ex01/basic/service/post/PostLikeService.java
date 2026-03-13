package com.ex01.basic.service.post;

import com.ex01.basic.entity.MemberEntity;
import com.ex01.basic.entity.post.PostEntity;
import com.ex01.basic.entity.post.PostLikeEntity;
import com.ex01.basic.repository.MemRepository;
import com.ex01.basic.repository.MemberRepository;
import com.ex01.basic.repository.post.PostLikeRepository;
import com.ex01.basic.repository.post.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostLikeService {
    private final PostLikeRepository postLikeRepository;
    private final MemRepository memberRepository;
    private final PostRepository postRepository;

    public void likePost(Long postId, int userId ){
        if( postLikeRepository.existsByMemberEntity_IdAndPostEntity_Id(userId, postId) ){
            throw new RuntimeException("이미 좋아요를 선택한 사용자");
        }

        MemberEntity memberEntity = memberRepository.getReferenceById(userId);
        PostEntity postEntity = postRepository.getReferenceById(postId);
        PostLikeEntity postLikeEntity = new PostLikeEntity(memberEntity, postEntity);

        postLikeRepository.save( postLikeEntity );
    }
    public void unLikePost(Long postId, int userId ){
        if( !postLikeRepository.existsByMemberEntity_IdAndPostEntity_Id(userId, postId) ){
            throw new RuntimeException("좋아요 취소할 수 없음...");
        }
        postLikeRepository.deleteByMemberEntity_IdAndPostEntity_Id(userId, postId);
    }
}


