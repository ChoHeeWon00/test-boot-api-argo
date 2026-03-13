package com.ex01.basic.dto;

import com.ex01.basic.entity.MemberEntity;
import lombok.*;
import org.springframework.beans.BeanUtils;
@Setter @Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class MemberDto {
    private int id;
    private String username;
    private String password;
    private String role;
    private String fileName;
    public MemberDto(MemberEntity memberEntity){
        BeanUtils.copyProperties( memberEntity, this );
    }
}
