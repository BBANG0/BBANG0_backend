package com.example.bbang0.adapter.persistance;

import com.example.bbang0.application.dto.Review.ReviewCreateReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class FavoriteDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void create(int bakeryId, String userId){
        String createFavoriteQuery = "insert into favorite (favorite_user_id, favorite_bakery_id ) VALUES (?,? )";
        Object[] createFavoriteParmas = new Object[]{userId,bakeryId};
        this.jdbcTemplate.update(createFavoriteQuery, createFavoriteParmas);
    }

    public void delete(String favoriteId, int bakeryId, String userId){
        String deleteFavoriteQuery = "DELETE FROM favorite WHERE favorite_id=?";
        String deleteFavoriteParams = favoriteId;
        this.jdbcTemplate.update(deleteFavoriteQuery,deleteFavoriteParams);
    }
}