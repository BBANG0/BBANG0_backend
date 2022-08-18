package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.BakeryDao;
import com.example.bbang0.adapter.persistance.FavoriteDao;
import com.example.bbang0.adapter.persistance.UserDao;
import com.example.bbang0.application.dto.Favorite.FavoriteCreateReqDto;
import com.example.bbang0.application.dto.Favorite.FavoriteResDto;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.bbang0.domain.exception.BaseResponseStatus.*;

@Service
public class FavoriteService {

    private final UserDao userDao;
    private final BakeryDao bakeryDao;
    private final FavoriteDao favoriteDao;
    private final JwtService jwtService;

    public FavoriteService(UserDao userDao, BakeryDao bakeryDao, FavoriteDao favoriteDao, JwtService jwtService) {
        this.userDao = userDao;
        this.bakeryDao = bakeryDao;
        this.favoriteDao = favoriteDao;
        this.jwtService = jwtService;
    }
    public List<FavoriteResDto> findAllByUserId(String userId) throws BaseException {
        return favoriteDao.findAll(userId);
    }

    public boolean favoriteOrNot(String bakeryId) throws BaseException {
        String userIdxByJwt = jwtService.getUserIdx();
        if ( favoriteDao.checkIsFavorite(bakeryId,userIdxByJwt)==1){
            return true;
        }
        else
            return false;
    }
    public String create(String bakeryId) throws BaseException {
        String userIdByJwt = jwtService.getUserIdx();
        FavoriteCreateReqDto favoriteCreateReqDto = new FavoriteCreateReqDto(userIdByJwt, bakeryId, Boolean.TRUE);
        if ( userDao.checkIdExist(favoriteCreateReqDto.getUser_id())==0){
            throw new BaseException(INVALID_USER);
        }
        if ( bakeryDao.checkIdExist(favoriteCreateReqDto.getBakery_id())==0){
            throw new BaseException(USER_NO_BAKERY);
        }
        // if 이미 좋아요 관계가 존재한다면 validation
        if (favoriteDao.checkIsFavorite(favoriteCreateReqDto.getBakery_id(),favoriteCreateReqDto.getUser_id())==1){
            throw new BaseException(TEST_FAIL);
        }
        favoriteDao.create(favoriteCreateReqDto.getBakery_id(),favoriteCreateReqDto.getUser_id());
        return "좋아요";
    }

    public String delete(int favoriteId, String bakeryId) throws BaseException {
        String userIdByJwt = jwtService.getUserIdx();
        FavoriteCreateReqDto favoriteCreateReqDto = new FavoriteCreateReqDto(userIdByJwt, bakeryId, Boolean.TRUE);
        //user, bakery가 유효한가?
        if ( userDao.checkIdExist(favoriteCreateReqDto.getUser_id())==0){
            throw new BaseException(INVALID_USER);
        }
        if ( bakeryDao.checkIdExist(favoriteCreateReqDto.getBakery_id())==0){
            throw new BaseException(USER_NO_BAKERY);
        }
        // if 이미 좋아요 관계가 없다면 validation
        System.out.println("bakeryId = " + favoriteCreateReqDto.getBakery_id()+"userId : "+favoriteCreateReqDto.getUser_id());
        System.out.println("test : "+favoriteDao.checkIsFavorite(favoriteCreateReqDto.getBakery_id(),favoriteCreateReqDto.getUser_id()));
        if (favoriteDao.checkIsFavorite(favoriteCreateReqDto.getBakery_id(),favoriteCreateReqDto.getUser_id())==0){
            throw new BaseException(TEST_FAIL);
        }
        favoriteDao.delete(favoriteId,Integer.parseInt(bakeryId), userIdByJwt);
        return "좋아요 취소";

    }
}
