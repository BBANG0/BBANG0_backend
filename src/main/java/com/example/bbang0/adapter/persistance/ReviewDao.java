package com.example.bbang0.adapter.persistance;

import com.example.bbang0.application.dto.Bakery.BakeryResDto;
import com.example.bbang0.application.dto.Review.ReviewCreateReqDto;
import com.example.bbang0.application.dto.Review.ReviewImgRes;
import com.example.bbang0.application.dto.Review.ReviewResDto;
import com.example.bbang0.domain.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReviewDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // findById 도 만들어야 할듯
    public List<ReviewResDto> findAll(String bakeryId){
        String selectReviewsQuery = " SELECT review_id,\n" +
                "          title,\n" +
                "          content,\n" +
                "          score,\n" +
                "          review_user_id\n" +
                "   FROM review\n" +
                "   WHERE review_bakery_id = ?";
        int selectBakeryParam = Integer.parseInt(bakeryId);
        return this.jdbcTemplate.query(selectReviewsQuery,
                (rs,rowNum) -> new ReviewResDto(
                        rs.getString("review_id"),
                        rs.getString("title"),
                        rs.getString("content"),
                        rs.getString("score"),
                        rs.getString("review_user_id"),
                        this.jdbcTemplate.query(
                                "SELECT ri.review_img_url_id,\n"+
                                        "            ri.review_img_url\n" +
                                        "        FROM review_img_url as ri\n" +
                                        "            join review as r on r.review_id = ri.review_id\n" +
                                        "        WHERE r.review_id = ?;\n",
                                (rk,rownum) -> new ReviewImgRes(
                                        rk.getInt("review_img_url_id"),
                                        rk.getString("review_img_url"))
                                ,rs.getString("review_id"))),selectBakeryParam);
    }

    public ReviewCreateReqDto create(String creatorId, int bakeryId, ReviewCreateReqDto reviewReqDto){
        // for test
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

    public boolean checkId(int reviewId,String deleterId){
        String getUserIdQuery = "select review_user_id from review where review_id = ?";
        return deleterId.equals(this.jdbcTemplate.queryForObject(getUserIdQuery,
                String.class,
                reviewId));
    }


}
