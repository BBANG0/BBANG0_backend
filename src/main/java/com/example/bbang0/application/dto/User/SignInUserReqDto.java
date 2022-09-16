package com.example.bbang0.application.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignInUserReqDto {
    private String user_id;
    private String password;
}
