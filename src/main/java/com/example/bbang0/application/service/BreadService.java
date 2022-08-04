package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.BreadDao;
import com.example.bbang0.application.dto.GetBreadResDto;
import com.example.bbang0.application.dto.PatchBreadReqDto;
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
                    postBreadReqDto.getBread_img_url(), postBreadReqDto.getBread_count(), postBreadReqDto.getBread_price(), postBreadReqDto.getBread_sale_price());

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

    public int checkBreadExist(int bread_id) throws BaseException{
        try{
            return breadDao.checkBreadExist(bread_id);
        } catch (Exception exception){
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public void modifyBread(int bread_id, PatchBreadReqDto patchBreadReqDto) throws BaseException {
        //존재하지 않는 빵집일 경우
        if(checkBakeryExist(patchBreadReqDto.getBakery_id()) == 0) {
            throw new BaseException(BAKERYS_EMPTY_BAKERY_ID);
        }
        //존재하지 않는 빵일 경우
        if(checkBreadExist(bread_id) == 0) {
            throw new BaseException(BREADS_EMPTY_BREAD_ID);
        }

        try {
            int result = breadDao.updateBread(bread_id, patchBreadReqDto.getBread_name(), patchBreadReqDto.getBread_img_url(),
                    patchBreadReqDto.getBread_content(), patchBreadReqDto.getBread_count(),patchBreadReqDto.getBread_price(), patchBreadReqDto.getBread_sale_price());

            //업데이트 과정 중 문제가 생겼을 경우
            if(result == 0) {
                throw new BaseException(MODIFY_FAIL_BREAD);
            }
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void modifyBreadCount(int bread_id, int bread_count) throws BaseException {
        //존재하지 않는 빵일 경우
        if(checkBreadExist(bread_id) == 0) {
            throw new BaseException(BREADS_EMPTY_BREAD_ID);
        }

        try {
            int result = breadDao.updateBreadCount(bread_id, bread_count);

            //업데이트 과정 중 문제가 생겼을 경우
            if(result == 0) {
                throw new BaseException(MODIFY_FAIL_BREAD);
            }
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public void deleteBread(int bread_id) throws BaseException {
        try {
            int result = breadDao.deleteBread(bread_id);

            //삭제 과정 중 문제가 생겼을 경우
            if(result == 0) {
                throw new BaseException(DELETE_FAIL_BREAD);
            }
        }
        catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}