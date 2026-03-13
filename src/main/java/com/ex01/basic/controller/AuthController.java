package com.ex01.basic.controller;

import com.ex01.basic.config.JwtUtil;
import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.dto.MemberRegDto;
import com.ex01.basic.exception.InvalidPasswordException;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginDto loginDto){
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(
                        loginDto.getUsername(), loginDto.getPassword() );
        Authentication authentication = null;
        try {
            authentication = authenticationManager.authenticate( token );
        } catch (BadCredentialsException e) {
            throw new InvalidPasswordException("비밀번호가 일치하지 않는다");
        }
        //로그인 성공이면 아래 내용 실행
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();

        //System.out.println("userDetails.getAuthorities() : " + userDetails.getAuthorities() );
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        String resultToken = jwtUtil.generateToken( userDetails.getUsername() , role );
        return ResponseEntity.ok( Collections.singletonMap("token", resultToken) );
    }
}