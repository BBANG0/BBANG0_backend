package com.example.bbang0.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class User {
    private String userIdx;
    private String passoword;
    private String userName;
    private String userEmail;
    private String isBakery;
}
