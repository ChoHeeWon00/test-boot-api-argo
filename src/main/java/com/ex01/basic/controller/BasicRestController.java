package com.ex01.basic.controller;

import com.ex01.basic.dto.BasicDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@RestController
//@RequestMapping("api/members")
public class BasicRestController {
    private List<BasicDto> list;
    public BasicRestController(){
        list = new ArrayList<BasicDto>();
        for(int i=0 ; i<3 ; i++){
            list.add( new BasicDto("aaa"+i , "test", i ) );
        }
        System.out.println("ctrl 생성자 실행");
    }
    @GetMapping("api/members")
    public ResponseEntity<List<BasicDto>> getMembers(){
        return ResponseEntity.ok(list);
    }
    @GetMapping("api/members/{username}")
    public ResponseEntity< BasicDto > getMembers(
                            @PathVariable("username") String username){
        BasicDto basicDto = list.stream()
                .filter( mem -> mem.getUsername().equals(username) )
                .findFirst()
                .orElse(null);
        if( basicDto == null ){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(basicDto);
    }

    @PostMapping("api/members")
    //fetch( url, {method:post,
    // HttpHeaders : {C-Y : json}
    // body : JSON.stringifiy( data )
    // }
    // )
    public ResponseEntity<String> postMembers( @RequestBody BasicDto basicDto ){
        boolean bool = list.add( basicDto );
        if( bool )
            return ResponseEntity.status(HttpStatus.CREATED).body("추가 성공"); //201
        // 400
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("문제 발생:잘못된요청");
    }

    @PostMapping("api/members/form")
    public ResponseEntity<String> postMembersForm( @ModelAttribute BasicDto basicDto ){
        boolean bool = list.add( basicDto );
        if( bool )
            return ResponseEntity.status(HttpStatus.CREATED).build(); //201
        // 400
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("문제 발생:잘못된요청");
    }

    @PutMapping("api/members")
    public ResponseEntity<Void>putMembers(
            @ModelAttribute BasicDto basicDto){
        boolean bool = list.stream()
                .filter(mem -> mem.getUsername().equals(basicDto.getUsername()))
                .findFirst()
                .map(mem -> {
                    mem.setPassword(basicDto.getPassword());
                    mem.setNum(basicDto.getNum());
                    return true;
                } )
                .orElse(false);
        if(bool)
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); //204
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build(); //400
    }

    @DeleteMapping("api/members/{username}")
    public ResponseEntity<Void> delMembers(
                        @PathVariable(name="username") String username){
        boolean bool = list.removeIf( mem -> mem.getUsername().equals(username) );
        if( bool )
            return ResponseEntity.noContent().build();
        return ResponseEntity.badRequest().build();
        //ResponseEntity.status( HttpStatus.OK ).body("aaaa");
        //ResponseEntity.ok("aaaa");
    }

    @GetMapping("api/test01")
    public ResponseEntity<String> getTest01(){
        return ResponseEntity.ok("통신 성공");
    }
    //get : 데이터 요청
    @GetMapping("api/test01/{username}")
    public ResponseEntity<String> getTest01(
            @PathVariable("username") String un  ){
        un += " 님의 정보 검색";
        return ResponseEntity.ok(un + "통신 성공");
    }
    //fetch( localhost/api/val/?usernmae = +변수(aaaa) )
    @GetMapping("api/val")
    public ResponseEntity<String> getTest02(
            @RequestParam("username") String un  ){
        un += " 님의 정보 검색";
        return ResponseEntity.ok(un + "통신 성공");
    }
    //post : 데이터 추가
    @PostMapping("api/test01")
    public ResponseEntity<String> postTest01(){
        return ResponseEntity.ok("post 통신 성공");
    }
    @PutMapping("api/test01")
    public ResponseEntity<String> putTest01(){
        return ResponseEntity.ok("put 통신 성공");
    }
    @DeleteMapping("api/test01")
    public ResponseEntity<String> deleteTest01(){
        return ResponseEntity.ok("delete 통신 성공");
    }
    @GetMapping("api/test02")
    public ResponseEntity< List<String> > getTest02(){
        ArrayList<String> list = new ArrayList<>();
        list.add("aaa"); list.add("bbb");
        return ResponseEntity.ok(list);
    }

}
