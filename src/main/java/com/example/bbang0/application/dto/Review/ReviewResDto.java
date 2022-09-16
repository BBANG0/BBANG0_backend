package com.example.bbang0.application.dto.Review;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ReviewResDto {
    private String review_id;
    private String title;
    private String content;
    private String score;
    private String review_user_id;
    private List<ReviewImgRes> imgs;
}
