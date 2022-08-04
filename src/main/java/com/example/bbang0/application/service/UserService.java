package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.UserDao;
import com.example.bbang0.application.dto.SignInUserResDto;
import com.example.bbang0.application.dto.SignUpUserReqDto;
import com.example.bbang0.domain.exception.BaseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bbang0.domain.exception.BaseResponseStatus.*;

@Service
public class UserService {
    private final UserDao userDao;

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }
    @Transactional
    public SignUpUserReqDto signUp(SignUpUserReqDto signupUserReqDto) throws BaseException {
        // Validation
        //ID 중복체크
        if ( userDao.checkIdExist(signupUserReqDto.getUser_id())!=0){
            throw new BaseException(EXIST_ID);
        }
        //이메일 중복체크
        if ( userDao.checkEmailExist(signupUserReqDto.getUser_email())!=0){
            throw new BaseException(EXIST_EMAIL);
        }
        //NULL 값 입력

        //잘못된 형식

        //password 암호화, token 발급만 구현하면 될듯
       userDao.signUp(signupUserReqDto);
        return signupUserReqDto;
    }

    @Transactional
    public String signIn(SignInUserResDto signInUserResDto) throws BaseException {
        // Validation
        // 회원가입 여부
        if ( userDao.checkIdExist(signInUserResDto.getUser_id())==0){
            throw new BaseException(INVALID_EMAIL);
        }

        // 아이디는 있지만 비밀번호 틀림
        if ( !userDao.checkUserPassword(signInUserResDto.getUser_id(),signInUserResDto.getPassword())){
            throw new BaseException(INVALID_PASSWORD);
        }

        return "token";
    }


}
