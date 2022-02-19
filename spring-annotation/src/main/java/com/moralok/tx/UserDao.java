package com.moralok.tx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

/**
 * @author moralok
 * @since 2020/12/23 3:45 下午
 */
@Repository
public class UserDao {

    @Autowired(required = false)
    private JdbcTemplate jdbcTemplate;

    public void insert() {
        String sql = "insert into tbl_user (username, age) values (?, ?)";
        String username = UUID.randomUUID().toString().substring(0, 5);
        jdbcTemplate.update(sql, username, 19);
    }
}
