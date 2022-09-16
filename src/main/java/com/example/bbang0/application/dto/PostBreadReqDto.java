package com.example.bbang0.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostBreadReqDto {
    private String bread_name;
    private String bread_img_url;
    private String bread_content;
    private int bread_count;
    private int bread_price;
    private int bread_sale_price;
}