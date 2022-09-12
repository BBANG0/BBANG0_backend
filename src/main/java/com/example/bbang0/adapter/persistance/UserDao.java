package com.example.bbang0.adapter.persistance;

import com.example.bbang0.application.dto.User.SignInUserReqDto;
import com.example.bbang0.application.dto.User.SignUpUserReqDto;
import com.example.bbang0.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public SignUpUserReqDto signUp(SignUpUserReqDto signUpUserReqDto){
        String createUserQuery = "insert into User ( user_id,password,user_name,nickname,user_phone,user_email,is_bakery ) VALUES (?,?,?,?,?,?,? )";
        Object[] createUserParmas = new Object[]{signUpUserReqDto.getUser_id(), signUpUserReqDto.getPassword(), signUpUserReqDto.getUser_name(), signUpUserReqDto.getNickname(), signUpUserReqDto.getUser_phone(), signUpUserReqDto.getUser_email(), signUpUserReqDto.getIs_bakery()};
        this.jdbcTemplate.update(createUserQuery, createUserParmas);
        return new SignUpUserReqDto(signUpUserReqDto.getUser_id(), signUpUserReqDto.getPassword(), signUpUserReqDto.getUser_name(), signUpUserReqDto.getNickname(), signUpUserReqDto.getUser_phone(), signUpUserReqDto.getUser_email(), signUpUserReqDto.getIs_bakery());
    }

    public User getUser(SignInUserReqDto signInUserReqDto) {
        String getPwdQuery = "select user_id, password, user_name, user_email, is_bakery from User where user_id = ?";
        String getUserIdParam = signInUserReqDto.getUser_id();

        return this.jdbcTemplate.queryForObject(getPwdQuery,
                (rs,rowNum)-> new User(
                        rs.getString("user_id"),
                        rs.getString("password"),
                        rs.getString("user_name"),
                        rs.getString("user_email"),
                        rs.getString("is_bakery")
                ),
                getUserIdParam
        );
    }

    public int checkIdExist(String user_id){
        String checkUserExistQuery = "select exists(select user_id from User where user_id = ?)";
        String checkUserExistParams = user_id;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }
    public int checkEmailExist(String user_email){
        String checkUserExistQuery = "select exists(select user_email from User where user_email = ?)";
        String checkUserExistParams = user_email;
        return this.jdbcTemplate.queryForObject(checkUserExistQuery,
                int.class,
                checkUserExistParams);
    }

    public boolean checkUserPassword(String user_id,String password){
        String checkUserPasswordQuery = "select password from User where user_id = ?";
        String checkUserExistParams = user_id;
        return password.equals(this.jdbcTemplate.queryForObject(checkUserPasswordQuery,
                String.class,
                checkUserExistParams));
    }

    public boolean checkUserIsBakery(String user_id){
        // db에 is_bakery를 boolea으로 처리하던지 or String으로 할거면 저장 형식을 고정해 놔야 한다.
        // 응 , 실제로 없어서 에러 터졋어
        String yes = "Yes";
        String checkUserIsBakeryQuery = "select is_bakery from user where user_id =? ";
        return yes.equals(this.jdbcTemplate.queryForObject(checkUserIsBakeryQuery,
                String.class,
                user_id));
    }
}