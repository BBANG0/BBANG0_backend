package com.example.bbang0.adapter.persistance;

import com.example.bbang0.domain.exception.BaseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

import static com.example.bbang0.domain.exception.BaseResponseStatus.*;

@Repository
public class BakeryUserDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(String userId, String bakeryId){

    }
    public boolean userIsSameBakery(String userId, String bakeryId) throws BaseException {
        // BakeryService에서 is_bakery==true가 전제 되어 있더라도 bakery_user_tb에는 없을 수 잇음
        // 추후 이런 부분도 validation 처리 해주셈
        System.out.println("BakeryUserDao.userIsSameBakery");
        // exist문 없으면 0 있으면 1
        String checkBakeryUserPresentQuery = "select exists(select bakery_id from bakery_user where user_id =?)";
        String checkBakeryUserQuery = "select bakery_id from bakery_user where user_id =?";
        if (this.jdbcTemplate.queryForObject(checkBakeryUserPresentQuery,
                int.class,
                userId)==0){
            throw new BaseException(USER_NO_BAKERY);
        }
        else {
            return bakeryId.equals(this.jdbcTemplate.queryForObject(checkBakeryUserQuery,
                    int.class,
                    userId)
            );
        }
    }
}
