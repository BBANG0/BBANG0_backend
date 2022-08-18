package com.example.bbang0.adapter.persistance;

import com.example.bbang0.application.dto.Bakery.BakeryResDto;
import com.example.bbang0.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class BakeryDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public BakeryResDto findById(String id){
        String getBakeryQuery = "select bakery_id, bakery_name, score, address, bakery_image,bakery_phone,bakery_content from bakery where bakery_id = ?";
        return this.jdbcTemplate.queryForObject(getBakeryQuery,
                (rs,rowNum)-> new BakeryResDto(
                        rs.getString("bakery_id"),
                        rs.getString("bakery_name"),
                        rs.getInt("score"),
                        rs.getString("address"),
                        rs.getString("bakery_image"),
                        rs.getString("bakery_phone"),
                        rs.getString("bakery_content")
                ),
                id
        );
    }
    public int checkIdExist(String bakery_id){
        String checkBakeryExistQuery = "select exists(select bakery_id from bakery where bakery_id = ?)";
        String checkBakeryExistParams = bakery_id;
        return this.jdbcTemplate.queryForObject(checkBakeryExistQuery,
                int.class,
                checkBakeryExistParams);
    }


//    user_id is_bakery True, False 여부 확인하고 만약 True라면 user_bakery 테이블 가야함
//    public int checkIsBakery(){
//
//    }
}
