package com.example.bbang0.adapter.controller;

import com.example.bbang0.application.dto.SignInUserResDto;
import com.example.bbang0.application.dto.SignUpUserReqDto;
import com.example.bbang0.application.service.UserService;
import com.example.bbang0.domain.exception.BaseException;
import com.example.bbang0.domain.exception.BaseResponse;
import org.springframework.http.HttpStatus;
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
        return this.userService.signUp(signupUserReqDto);
    }

    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignInUserResDto signInUserResDto) throws BaseException {
        return this.userService.signIn(signInUserResDto);
    }


}
