package com.ex01.basic.dto;

import lombok.*;

@Setter @Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegDto {
    private String username;
    private String password;
    //private String role;
    private String fileName;
}
