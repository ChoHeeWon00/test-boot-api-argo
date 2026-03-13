package com.ex01.basic.controller;

import com.ex01.basic.dto.LoginDto;
import com.ex01.basic.dto.MemberDto;
import com.ex01.basic.dto.MemberRegDto;
import com.ex01.basic.service.MemberFileService;
import com.ex01.basic.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Tag(name="MemberAPI" , description = "회원 도메인 API")
@RestController
@RequestMapping("/members")
@Slf4j
public class MemberController {
    private MemberService memberService;
    @Autowired
    private MemberFileService memberFileService;

    public MemberController( MemberService memberService){
        this.memberService = memberService;
    }

    @GetMapping("{fileName}/image")
    @Operation(
            summary = "회원 이미지 조회",
            description = "프로필의 이미지 다운로드"
    )
    @ApiResponses({
            @ApiResponse( responseCode = "200" , description = "이미지 조회 성공",
            content = @Content(
                    mediaType = "image/*",
                    schema = @Schema(implementation = Byte.class )
            )),
            @ApiResponse( responseCode = "404", description = "이미지 없음")
    })
    public ResponseEntity<byte[]> getMemberImage(@PathVariable("fileName") String fileName){
        byte[] imageByte = null;
        imageByte = memberFileService.getImage( fileName );
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpg")
                .body(imageByte);
    }

    //@PostMapping("/login") //members/login
    @Operation(
            summary = "로그인",
            description = "username과 password 인증"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "로그인 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class, example = "0")
                    )
            ),
            @ApiResponse(responseCode = "401", description = "아이디 또는 비번 틀림",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Integer.class, example = "1")
                    ))
    })
    public ResponseEntity<Integer> login(@RequestBody LoginDto loginDto){
        //System.out.println("loginDto => " + loginDto );
        //try{
            memberService.login( loginDto );
        //} catch (InvalidLoginException e) {
        //    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(1);
        //}
        return ResponseEntity.ok(0);
    }

    @GetMapping("test")
    public ResponseEntity<String> getTest(){
        System.out.println("service : " + memberService );
        memberService.serviceTest();
        return ResponseEntity.ok("성공");
    }

    @GetMapping
    @Operation(
            summary = "모든 사용자 Read",
            description = "회원 목록 조회"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Map.class, example = """
                                    {
                                        "totalPage" : 10,
                                        "currentPage" : 1,
                                        "list" : [
                                                        {
                                                            "id" : 1,
                                                            "username" : "aaa",
                                                            "password" : "111",
                                                            "role" : "USER"
                                                        }
                                                    ]
                                    }
                                    """)
/*
                            array = @ArraySchema(
                                    schema = @Schema(implementation = MemberDto.class)
                            )

 */
                    )
            ),
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="null")
                    ))
    })
    public ResponseEntity< Map<String, Object> > getList(
            @RequestParam(  value="start", defaultValue = "0") int start ){//start
        System.out.println("start : "+start);
        Map<String, Object> map = null;
        //try {
        map = memberService.getList( start );
        //} catch (MemberNotFoundException e) {
            //e.getMessage();
          //  return ResponseEntity
          //          .status(HttpStatus.NOT_FOUND).body( list );
        //}
        return ResponseEntity.ok(map);
    }

    @GetMapping("/{id}") // /members/{id}
    @SecurityRequirement(name="JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = MemberDto.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="null")
                    ))
    })
    public ResponseEntity<MemberDto> getOne(@PathVariable("id") int id){
        MemberDto memberDto = null;
        //System.out.println("연결 확인 : "+id);
       // try{
            memberDto = memberService.getOne( id );
        //} catch (MemberNotFoundException e) {
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        //}
        //sel * fom member whe id={id}
        return ResponseEntity.ok(memberDto);
    }
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @SecurityRequirement(name="JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "수정 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "수정 사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    ))
    })
    public ResponseEntity<Void> update(
            @RequestParam(value="file", required = false) MultipartFile multipartFile,
            @ParameterObject @ModelAttribute MemberDto memberDto,
                                        @PathVariable("id") int id ,
                                        Authentication authentication ){
        memberService.modify( id , memberDto, multipartFile , authentication.getName() );

        return ResponseEntity.ok().build();
    }
    @DeleteMapping("{id}")
    @SecurityRequirement(name="JWT")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "삭제 성공(내용 없음)",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    )
            ),
            @ApiResponse(responseCode = "404", description = "삭제 사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = Void.class)
                    ))
    })
    public ResponseEntity<Void> deleteMember(@PathVariable("id") int id,
                                             @RequestBody String fileName,
                                             Authentication authentication){
        //try {
            memberService.delMember( id, authentication.getName() );
            memberFileService.deleteFile( fileName );
       // } catch (MemberNotFoundException e) {
        //    return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        //}
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "추가 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = String.class, example = "추가성공")
                    )
            ),
            @ApiResponse(responseCode = "409", description = "중복 id",
                    content = @Content(
                            mediaType = "application/json",
                            examples = @ExampleObject(value="중복된 id 입니다")
                    ))
    })
    public ResponseEntity<String> register(
            @RequestParam( value="file", required = false ) MultipartFile multipartFile,
                            @ParameterObject
                            @ModelAttribute MemberRegDto memberRegDto){
        //System.out.println("multipartFile : " + multipartFile );
        memberService.insert( memberRegDto , multipartFile);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원 가입 성공");
    }

    @GetMapping("/codedeploy") // members/codedeploy
    public ResponseEntity<String> codeDeploy(HttpServletRequest req){
        return ResponseEntity.ok("자동배포 확인 : "+req.getLocalAddr() );
    }


}





