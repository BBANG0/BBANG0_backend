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

    public ReviewService(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;
    }

    public List<ReviewResDto> findAll(String bakeryId) throws BaseException {
        return reviewDao.findAll(bakeryId);
    }
    public String create(int bakeryId, String userIdxByJwt, ReviewCreateReqDto reviewCreateReqDto){
        reviewDao.create(userIdxByJwt,bakeryId, reviewCreateReqDto);
        return "test";
    }

    public void update(int reviewId, String updaterId, ReviewCreateReqDto reviewReqDto) throws BaseException {
        //updator validation에 대한 처리 필요
        if(!reviewDao.checkId(reviewId, updaterId)){
            throw new BaseException(INVALID_JWT);
        }
        if (reviewDao.update(reviewId,reviewReqDto)==0){
            throw new BaseException(FAIL_REVIEW_UPDATE);
        }
    }

    public void delete(int reviewId, String deleterId) throws BaseException {
        if(!reviewDao.checkId(reviewId, deleterId)){
            throw new BaseException(INVALID_JWT);
        }
        if (reviewDao.delete(reviewId)==0){
            throw new BaseException(FAIL_REVIEW_DELETE);
        }
    }
}