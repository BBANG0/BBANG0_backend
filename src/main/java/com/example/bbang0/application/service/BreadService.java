package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.BreadDao;
import com.example.bbang0.application.dto.GetBreadResDto;
import com.example.bbang0.application.dto.PostBreadReqDto;
import com.example.bbang0.application.dto.PostBreadResDto;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

            return new PostBreadResDto(bread_id);
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

    public List<GetBreadResDto> retrieveBreads(int bakery_id) throws BaseException {
        if(checkBakeryExist(bakery_id) == 0) {
            throw new BaseException(BAKERYS_EMPTY_BAKERY_ID);
        }
        try{
            List<GetBreadResDto> getBreads = breadDao.selectBreads(bakery_id);

            return getBreads;
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public int checkBakeryExist(int bakery_id) throws BaseException{
        try{
            return breadDao.checkBakeryExist(bakery_id);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }

}