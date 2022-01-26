package com.ssafy.deathnotelive.controller;

import com.ssafy.deathnotelive.dto.LoginDto;
import com.ssafy.deathnotelive.dto.UserDto;
import com.ssafy.deathnotelive.service.UserService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("user")
@Api(tags = {"User Controller"})
@Slf4j
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @PostMapping("login")
    @ApiOperation(value = "로그인", notes = "<strong>아이디와 패스워드</strong>를 통해 로그인 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<LoginDto.Response> register(
            @RequestBody @ApiParam(value = "로그인 정보", required = true) LoginDto.Request requestInfo) {
        String userId = requestInfo.getUserId();
        String password = requestInfo.getPassword();
        String token = userService.logIn(userId, password);
        return ResponseEntity.ok(LoginDto.Response.of(200, "Success", token));
    }

    @PostMapping("signup")
    @ApiOperation(value = "회원 가입", notes = "<strong>아이디와 패스워드</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "인증 실패"),
            @ApiResponse(code = 404, message = "사용자 없음"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity register(
            @RequestBody @ApiParam(value = "회원가입 정보", required = true) UserDto.UserRegist registerInfo) {
        System.out.println(registerInfo.getUserId() + registerInfo.getEmail() + registerInfo.getPassword());
        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음.
        userService.signup(registerInfo);
        return new ResponseEntity("회원가입에 성공했습니다.",HttpStatus.OK);
    }


}
