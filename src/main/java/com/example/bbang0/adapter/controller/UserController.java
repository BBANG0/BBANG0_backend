package com.example.bbang0.adapter.controller;

import com.example.bbang0.application.dto.SignInUserReqDto;
import com.example.bbang0.application.dto.SignInUserResDto;
import com.example.bbang0.application.dto.SignUpUserReqDto;
import com.example.bbang0.application.service.UserService;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @ResponseBody
    @PostMapping("/sign-up")
    public SignUpUserReqDto signUp(@RequestBody SignUpUserReqDto signupUserReqDto) throws BaseException {
        System.out.println("test" );
        return this.userService.signUp(signupUserReqDto);
    }

    @PostMapping("/sign-in")
    public SignInUserResDto signIn(@RequestBody SignInUserReqDto signInUserReqDto) throws BaseException {
        return this.userService.signIn(signInUserReqDto);
    }

}
