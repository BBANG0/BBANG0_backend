package com.example.bbang0.adapter.persistance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class BreadDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public int insertBread(int bakery_id, String bread_name, String bread_img_url, String bread_content, int bread_price, int bread_count){
        String insertBreadQuery = "INSERT INTO Bread(bakery_id, bread_name, bread_content, bread_img_url, bread_count, bread_price) VALUES (?, ?, ?, ?, ?, ?)";
        Object []insertBreadParams = new Object[] {bakery_id, bread_name, bread_content, bread_img_url, bread_count, bread_price};
        this.jdbcTemplate.update(insertBreadQuery,
                insertBreadParams);

        //자동으로 가장 마지막에 들어간 idx 값을 리턴해줌
        String lastInsertIdQuery="SELECT last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // 우선 BreadDao에 작성
    // user_id를 입력으로 받고, breadId를 리턴해주는 함수
    public int findBakeryId(String user_id) {
        String findBakeryIdQuery = "SELECT ad_bakery_id FROM adjecency WHERE ad_user_id = ?";
        String findBakeryIdParams = user_id;
        return this.jdbcTemplate.queryForObject(findBakeryIdQuery,
                int.class,
                findBakeryIdParams);
    }
}
