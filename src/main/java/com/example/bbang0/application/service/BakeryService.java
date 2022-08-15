package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.BakeryDao;
import com.example.bbang0.adapter.persistance.BakeryUserDao;
import com.example.bbang0.adapter.persistance.UserDao;
import com.example.bbang0.application.dto.Bakery.BakeryResDto;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.stereotype.Service;

@Service
public class BakeryService {

    private final UserDao userDao;
    private final BakeryDao bakeryDao;

    private final BakeryUserDao bakeryUserDao;

    private final JwtService jwtService;

    public BakeryService(UserDao userDao, BakeryDao bakeryDao, BakeryUserDao bakeryUserDao, JwtService jwtService) {
        this.userDao = userDao;
        this.bakeryDao = bakeryDao;
        this.bakeryUserDao = bakeryUserDao;
        this.jwtService = jwtService;
    }

    public BakeryResDto findById( String bakeryId) throws BaseException {
        String userId = jwtService.getUserIdx();
        System.out.println("userId = " + userId + ", bakeryId = " + bakeryId);

        if(userDao.checkUserIsBakery(userId)){
            if (bakeryUserDao.userIsSameBakery(userId,bakeryId)){
                // 일치하는 베이커리까지는 확인가능
                // 그러나 위에서 베이커리는 맞지만 일치하지 않으면 아무것도 못찾아서 SQL문 오류
                System.out.println("test right bakery");
            }
        }

        // if 문을 통과하더라도 정상적인 베이커리 페이지 출력

        return bakeryDao.findById(bakeryId);
    }
}
