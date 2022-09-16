package com.example.bbang0.application.dto.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ReviewCreateReqDto {
    private String title;
    private String content;
    private String score;
}
