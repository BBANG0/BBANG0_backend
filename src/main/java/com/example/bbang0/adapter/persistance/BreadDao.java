package com.example.bbang0.adapter.persistance;

import com.example.bbang0.application.dto.GetBreadResDto;
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


    public int insertBread(int bakery_id, String bread_name, String bread_img_url, String bread_content, int bread_count, int bread_price, int bread_sale_price){
        String insertBreadQuery = "INSERT INTO Bread(bakery_id, bread_name, bread_content, bread_img_url, bread_count, bread_price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Object []insertBreadParams = new Object[] {bakery_id, bread_name, bread_content, bread_img_url, bread_count, bread_price, bread_sale_price};
        this.jdbcTemplate.update(insertBreadQuery,
                insertBreadParams);

        //자동으로 가장 마지막에 들어간 idx 값을 리턴해줌
        String lastInsertIdQuery="SELECT last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }

    // 우선 BreadDao에 작성
    // user_id를 입력으로 받고, breadId를 리턴해주는 함수
    public int findBakeryId(String user_id) {
        String findBakeryIdQuery = "SELECT bakery_id FROM bakery_user WHERE user_id = ?";
        String findBakeryIdParams = user_id;
        return this.jdbcTemplate.queryForObject(findBakeryIdQuery,
                int.class,
                findBakeryIdParams);
    }

    public int checkBakeryExist(int bakery_id) {
        String checkBakeryExistQuery = "SELECT EXISTS(SELECT bakery_id from Bakery where bakery_id = ?)";
        int checkBakeryExistParams = bakery_id;

        return this.jdbcTemplate.queryForObject(checkBakeryExistQuery,
                int.class,
                checkBakeryExistParams);
    }

    public int checkBreadExist(int bread_id) {
        String checkBreadExistQuery = "SELECT EXISTS(SELECT bread_id from Bread where bread_id = ?)";
        int checkBreadExistParams = bread_id;

        return this.jdbcTemplate.queryForObject(checkBreadExistQuery,
                int.class,
                checkBreadExistParams);
    }

    public List<GetBreadResDto> selectBreads(int bakery_id){
        String selectBreadsQuery = "SELECT b.bread_id, b.bakery_id, b.bread_name, b.bread_img_url, b.bread_content, b.bread_count, b.bread_price, b.bread_sale_price " +
                "CASE WHEN timestampdiff(second, b.updated_at, current_timestamp) < 60" +
                "THEN concat(timestampdiff(second, b.updated_at, current_timestamp), '초 전')" +
                "WHEN timestampdiff(minute, b.updated_at, current_timestamp) < 60" +
                "THEN concat(timestampdiff(minute, b.updated_at, current_timestamp), '분 전')" +
                "WHEN timestampdiff(hour, b.updated_at, current_timestamp) < 24" +
                "THEN concat(timestampdiff(hour, b.updated_at, current_timestamp), '시간 전')" +
                "ELSE timestampdiff(year, b.updated_at, current_timestamp)" +
                "END AS updated_at " +
                "FROM Bread as b WHERE bakery_id = ?";

        int selectBreadsParams = bakery_id;

        return this.jdbcTemplate.query(selectBreadsQuery,
                (rs,rowNum) -> new GetBreadResDto(
                        rs.getInt("bread_id"),
                        rs.getInt("bakery_id"),
                        rs.getString("bread_name"),
                        rs.getString("bread_img_url"),
                        rs.getString("bread_content"),
                        rs.getInt("bread_count"),
                        rs.getInt("bread_price"),
                        rs.getInt("b.bread_sale_price"),
                        rs.getString("updated_at")
                        ),
                selectBreadsParams);
    }

    //전체 수정 기능
    public int updateBread(int bread_id, String bread_name, String bread_img_url, String bread_content, int bread_count, int bread_price, int bread_sale_price){
        String updateBreadQuery = "UPDATE Bread SET bread_name=?, bread_img_url=?, bread_content=?, bread_count=?, bread_price=?, bread_sale_price=?, WHERE bread_id=?";
        Object [] updateBreadParams = new Object[] {bread_name, bread_img_url, bread_content, bread_count, bread_price, bread_sale_price };
        return this.jdbcTemplate.update(updateBreadQuery, updateBreadParams);
    }

    public int updateBreadCount(int bread_id, int bread_count){
        String updateBreadQuery = "UPDATE Bread SET bread_count=?, WHERE bread_id=?";
        Object [] updateBreadParams = new Object[] { bread_id, bread_count };
        return this.jdbcTemplate.update(updateBreadQuery, updateBreadParams);
    }

    public int deleteBread(int bread_id){
        String deleteBreadQuery = "DELETE FROM Bread WHERE bread_id=?";
        int deleteBreadParams = bread_id;
        return this.jdbcTemplate.update(deleteBreadQuery, deleteBreadParams);
    }
}

