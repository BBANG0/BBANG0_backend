package com.example.bbang0.adapter.controller;

import com.example.bbang0.application.dto.Review.ReviewCreateReqDto;
import com.example.bbang0.application.dto.Review.ReviewResDto;
import com.example.bbang0.application.service.ReviewService;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/review")
public class ReviewController {
    private final ReviewService reviewService;


    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public List<ReviewResDto> findAll(
            @RequestParam(value="bakery") String bakeryId
    ) throws BaseException {
        return this.reviewService.findAll(bakeryId);
    }

    @PostMapping("")
    public String create(
            @AuthenticationPrincipal String creatorId,
            @RequestParam(value="bakery") int bakeryId,
            @RequestBody ReviewCreateReqDto reviewCreateReqDto) throws BaseException {
        reviewService.create(creatorId, reviewCreateReqDto);
        return "리뷰가 생성되었습니다";
    }

    @PutMapping("/{reviewId}")
    public String update(
            @PathVariable("reviewId") int reviewId,
            @RequestParam(value="bakery") String bakeryId,
            @RequestBody ReviewCreateReqDto reviewReqDto
    ) throws BaseException {
        reviewService.update(bakeryId,reviewId, reviewReqDto);
        return "수정 완료했습니다.";
    }

    @DeleteMapping("/{reviewId}")
    public String delete(
            @RequestParam(value="bakery") String bakeryId,
            @PathVariable("reviewId") int reviewId
    ) throws BaseException {
        reviewService.delete(reviewId);
        return "삭제 완료했습니다.";
    }
//    @PostMapping("")
//    public String addImage(){
//
//    }


}
