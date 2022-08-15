package com.example.bbang0.application.dto.Bakery;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BakeryResDto {
    private String bakery_id;
    private String bakery_name;
    private int score;
    private String address;
    private String bakery_image;
    private String bakery_phone;
    private String bakery_content;
}
