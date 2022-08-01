package com.example.bbang0.application.dto;

public class PostBreadReqDto {
    private String user_id;
    private String bread_name;
    private String bread_img_url;
    private String bread_content;
    private int bread_count;
    private int bread_price;

    public String getUser_id() { return user_id; };
    public String getBread_name() { return bread_name; };
    public String getBread_img_url() { return bread_img_url; };
    public String getBread_content() { return bread_content; };
    public int getBread_count() { return bread_count; };
    public int getBread_price() { return bread_price; };

}


