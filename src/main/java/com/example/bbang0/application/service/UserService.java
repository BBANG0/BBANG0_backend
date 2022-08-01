package com.example.bbang0.application.service;

import com.example.bbang0.adapter.persistance.UserDao;
import com.example.bbang0.application.dto.SignInUserReqDto;
import com.example.bbang0.application.dto.SignInUserResDto;
import com.example.bbang0.application.dto.SignUpUserReqDto;
import com.example.bbang0.config.SHA256;
import com.example.bbang0.domain.exception.BaseException;
import com.example.bbang0.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.bbang0.domain.exception.BaseResponseStatus.*;

@Service
public class UserService {
    private final UserDao userDao;
    private final JwtService jwtService;

    @Autowired
    public UserService(UserDao userDao, JwtService jwtService){
        this.userDao = userDao;
        this.jwtService = jwtService;
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
        String pwd = new SHA256().encrypt(signupUserReqDto.getPassword());
        signupUserReqDto.setPassword(pwd);
        //password 암호화, token 발급만 구현하면 될듯
        userDao.signUp(signupUserReqDto);
        return signupUserReqDto;
    }

    @Transactional
    public SignInUserResDto signIn(SignInUserReqDto signInUserReqDto) throws BaseException {
        // 회원가입 여부
        if ( userDao.checkIdExist(signInUserReqDto.getUser_id())==0){
            throw new BaseException(INVALID_EMAIL);
        }
        User user = userDao.getUser(signInUserReqDto);
        String pwd = new SHA256().encrypt(signInUserReqDto.getPassword());

        // Validation
        // 아이디는 있지만 비밀번호 틀림
        if ( !userDao.checkUserPassword(signInUserReqDto.getUser_id(),pwd)){
            throw new BaseException(INVALID_PASSWORD);
        }
        String userIdx = user.getUserIdx();
        String jwt = jwtService.createJwt(userIdx);
        String isBakery = user.getIsBakery();
        return new SignInUserResDto(jwt,isBakery);
    }
}
