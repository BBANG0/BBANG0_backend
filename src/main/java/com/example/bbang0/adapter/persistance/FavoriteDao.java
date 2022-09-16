package com.example.bbang0.adapter.persistance;

import com.example.bbang0.application.dto.Favorite.FavoriteResDto;
import com.example.bbang0.application.dto.Review.ReviewCreateReqDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class FavoriteDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public List<FavoriteResDto> findAll(String userId){
        String selectFavoritesQuery = "Select favorite_id,favorite_user_id,favorite_bakery_id\n" +
                "from favorite\n" +
                "where favorite_user_id = ?";
        return this.jdbcTemplate.query(selectFavoritesQuery,
                (rs, rowNum) -> new FavoriteResDto(
                        rs.getInt("favorite_id"),
                        rs.getString("favorite_user_id"),
                        rs.getString("favorite_bakery_id")
                ),
                userId);
    }
    public void create(String bakeryId, String userId){
        String createFavoriteQuery = "insert into favorite (favorite_user_id, favorite_bakery_id ) VALUES (?,? )";
        Object[] createFavoriteParmas = new Object[]{userId,bakeryId};
        this.jdbcTemplate.update(createFavoriteQuery, createFavoriteParmas);
    }

    public void delete(int favoriteId, int bakeryId, String userId){
        String deleteFavoriteQuery = "DELETE FROM favorite WHERE favorite_id=?";
        this.jdbcTemplate.update(deleteFavoriteQuery,favoriteId);
    }

    public int checkIsFavorite(String bakeryId ,String userId){
        String checkIsFavoriteQuery = "select exists(\n" +
                "Select favorite_bakery_id\n" +
                "from favorite\n" +
                "where favorite_user_id=? and favorite_bakery_id=?\n" +
                "           )";
        Object[] checkIsFavoriteParams = new Object[]{userId,bakeryId};
        return this.jdbcTemplate.queryForObject(checkIsFavoriteQuery,
                int.class,
                checkIsFavoriteParams);
    }
}
