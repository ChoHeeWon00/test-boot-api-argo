package com.ex01.basic.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@SecurityRequirement(name="JWT")
public class AdminController {
    @GetMapping
    public String index(){
        return "admin 접속 가능";
    }
}
