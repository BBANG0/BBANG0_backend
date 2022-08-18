package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.ReviewDao;
import com.example.bbang0.application.dto.Review.ReviewCreateReqDto;
import com.example.bbang0.application.dto.Review.ReviewResDto;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bbang0.domain.exception.BaseResponseStatus.*;

@Service
public class ReviewService {
    private final ReviewDao reviewDao;
    private final JwtService jwtService;

    public ReviewService(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;
        this.jwtService = jwtService;
    }

    public List<ReviewResDto> findAll(String bakeryId) throws BaseException {
        String userIdxByJwt = jwtService.getUserIdx();
        return reviewDao.findAll(bakeryId);
    }
    public String create(int bakeryId, ReviewCreateReqDto reviewCreateReqDto) throws BaseException {
        String userIdxByJwt = jwtService.getUserIdx();

        System.out.println("creatorId = " + userIdxByJwt  + ", reviewCreateReqDto = " + reviewCreateReqDto.getTitle());
        reviewDao.create(userIdxByJwt,bakeryId, reviewCreateReqDto);
        return "test";
    }

    public void update(String bakeryId, int reviewId, ReviewCreateReqDto reviewReqDto) throws BaseException {
        //updator validation에 대한 처리 필요

        System.out.println("updatorId = " + bakeryId + ", reviewId = " + reviewId + ", reviewReqDto = " + reviewReqDto.getTitle());
        if (reviewDao.update(reviewId,reviewReqDto)==0){
            throw new BaseException(FAIL_REVIEW_UPDATE);
        }
    }

    public void delete(int reviewId) throws BaseException {
        String deleterId = jwtService.getUserIdx();
        System.out.println("deleterId = " + deleterId);
        if(!reviewDao.checkId(reviewId, deleterId)){
            throw new BaseException(INVALID_JWT);
        }
        if (reviewDao.delete(reviewId)==0){
            throw new BaseException(FAIL_REVIEW_DELETE);
        }
    }
}