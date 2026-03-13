package com.ex01.basic.controller.post;

import com.ex01.basic.config.security.CustomUserDetails;
import com.ex01.basic.dto.post.PostAllDto;
import com.ex01.basic.dto.post.PostInsertDto;
import com.ex01.basic.dto.post.PostDetailDto;
import com.ex01.basic.dto.post.PostModifyDto;
import com.ex01.basic.service.post.PostService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    @PostMapping
    @SecurityRequirement(name="JWT")
    public ResponseEntity<Map<String, Object>> insert(
            @ParameterObject @ModelAttribute PostInsertDto postInsertDto,
                                            Authentication authentication ){
        postService.insert( postInsertDto, authentication.getName() );

        Map<String, Object> map = new HashMap<>();
        map.put("status", "SUCCESS");
        map.put("message", "데이터 추가 성공");

        return ResponseEntity.ok(map);
    }
    @GetMapping
    @SecurityRequirement(name="JWT")
    public ResponseEntity<List<PostAllDto>> getPost(
                    @AuthenticationPrincipal CustomUserDetails userDetails  ){
        //System.out.println("userDetails : " + userDetails);
        int userId = 0;
        if( userDetails != null)
            userId = userDetails.getId();
        return ResponseEntity.ok( postService.getPost(userId) );
    }
    @GetMapping("{id}")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<PostDetailDto> getPostOne(
                                @PathVariable("id") Long id,
                                Authentication authentication ){

        CustomUserDetails userDetails = (CustomUserDetails)authentication.getPrincipal();
        //System.out.println("user details : "+ userDetails);

        return ResponseEntity.ok( postService.getPostOne( id , userDetails.getId() ) );
    }
    @DeleteMapping("{id}")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<Void> postDelete(@PathVariable("id") Long id,
                                           Authentication authentication ){
        postService.postDelete( id, authentication.getName() );
        return ResponseEntity.ok().build();
    }
    @PutMapping("{id}")
    @SecurityRequirement(name="JWT")
    public ResponseEntity<Void> update(@PathVariable("id") Long id,
                                       @ParameterObject @ModelAttribute PostModifyDto postModifyDto,
                                       Authentication authentication ){
        postService.update(id, postModifyDto, authentication.getName() );
        return ResponseEntity.ok().build();
    }

}









