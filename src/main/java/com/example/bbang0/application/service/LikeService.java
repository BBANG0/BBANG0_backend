package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.BakeryUserDao;
import org.springframework.stereotype.Service;

@Service
public class LikeService {

    private final BakeryUserDao bakeryUserDao;
    private final JwtService jwtService;


    public LikeService(BakeryUserDao bakeryUserDao, JwtService jwtService) {
        this.bakeryUserDao = bakeryUserDao;
        this.jwtService = jwtService;
    }

    public void create( String userId, String bakeryId){
        // 즐겨찾기 추가 전에 즐겨찾기 여부 확인
        bakeryUserDao.create(userId, bakeryId);
    }

    public void delete(String userId, String bakeryId){

    }


}
