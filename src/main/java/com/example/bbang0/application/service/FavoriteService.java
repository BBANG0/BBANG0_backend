package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.FavoriteDao;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.stereotype.Service;

@Service
public class FavoriteService {

    private final FavoriteDao favoriteDao;
    private final JwtService jwtService;

    public FavoriteService(FavoriteDao favoriteDao, JwtService jwtService) {
        this.favoriteDao = favoriteDao;
        this.jwtService = jwtService;
    }

    public String create(String bakeryId) throws BaseException {
        String userIdByJwt = jwtService.getUserIdx();
        favoriteDao.create(Integer.parseInt(bakeryId), userIdByJwt);
        return "좋아요";
    }

    public String delete(String favoriteId, String bakeryId) throws BaseException {
        String userIdByJwt = jwtService.getUserIdx();
        favoriteDao.delete(favoriteId,Integer.parseInt(bakeryId), userIdByJwt);
        return "좋아요 취소";

    }
}