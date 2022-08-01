package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.BreadDao;
import com.example.bbang0.application.dto.PostBreadReqDto;
import com.example.bbang0.application.dto.PostBreadResDto;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bbang0.domain.exception.BaseResponseStatus.*;
import static com.example.bbang0.domain.exception.BaseResponseStatus.DATABASE_ERROR;

@Service
public class BreadService {

    private final BreadDao breadDao;

    @Autowired
    public BreadService(BreadDao breadDao){
        this.breadDao = breadDao;
    }

    @Transactional
    public PostBreadResDto createBread(int bakery_id, PostBreadReqDto postBreadReqDto) throws BaseException {
        try{

            int bread_id = breadDao.insertBread(bakery_id, postBreadReqDto.getBread_name(), postBreadReqDto.getBread_content(),
                    postBreadReqDto.getBread_img_url(), postBreadReqDto.getBread_count(), postBreadReqDto.getBread_price());

            return new PostBreadResDto();
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int findBakeryIdByUserId(String user_id) throws BaseException{
        try{
            return breadDao.findBakeryId(user_id);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
