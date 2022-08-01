package com.example.bbang0.adapter.persistance;

import com.example.bbang0.application.dto.Review.ReviewCreateReqDto;
import com.example.bbang0.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class ReviewDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
    public ReviewCreateReqDto create(String creatorId, ReviewCreateReqDto reviewReqDto){
        // for test
        int bakeryId = 1;
        String createReviewQuery = "insert into review (review_user_id,review_bakery_id, title, content, score ) VALUES (?,?,?,?,? )";
        Object[] createReviewParmas = new Object[]{creatorId,bakeryId,reviewReqDto.getTitle(),reviewReqDto.getContent(),reviewReqDto.getScore()};
        this.jdbcTemplate.update(createReviewQuery, createReviewParmas);
        return new ReviewCreateReqDto(reviewReqDto.getTitle(),reviewReqDto.getContent(),reviewReqDto.getScore());
    }

    public int update(int reviewIdx, ReviewCreateReqDto reviewReqDto){
        String updateReviewQuery = "UPDATE review SET title=?,content=?,score=? WHERE review_id=?";
        Object []updateReviewParams = new Object[]{reviewReqDto.getTitle(),reviewReqDto.getContent(),reviewReqDto.getScore(),reviewIdx};
        return this.jdbcTemplate.update(updateReviewQuery,updateReviewParams);
    }

    //status 값을 이용한 delete 처리도 가능.
    public int delete(int reviewIdx){
        String deleteReviewQuery = "DELETE FROM review WHERE review_id=?";
        int deleteReviewParams = reviewIdx;
        return this.jdbcTemplate.update(deleteReviewQuery,deleteReviewParams);
    }

    public boolean findById(int reviewId,String deleterId){
        String getUserIdQuery = "select review_user_id from review where review_id = ?";
        return deleterId.equals(this.jdbcTemplate.queryForObject(getUserIdQuery,
                String.class,
                reviewId));
    }


}
