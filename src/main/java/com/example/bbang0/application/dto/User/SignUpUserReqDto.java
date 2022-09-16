package com.example.bbang0.application.dto.User;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpUserReqDto {
    private String user_id;
    private String password;
    private String user_name;
    private String nickname;
    private int user_phone;
    private String user_email;
    private String is_bakery;
}
