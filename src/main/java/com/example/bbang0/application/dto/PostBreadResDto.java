package com.example.bbang0.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostBreadResDto {
    private int bread_id;
    private String bread_name;
    private String bread_img_url;
    private String bread_content;
    private int bread_count;
    private int bread_price;
    private int bread_sale_price;

    public PostBreadResDto(int bread_id) {};
}

