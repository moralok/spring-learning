package com.moralok.mybatis;

import com.moralok.mybatis.bean.Employee;
import com.moralok.mybatis.mapper.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author moralok
 * @since 2021/1/13 1:56 下午
 */
public class CacheTest {

    @Test
    void firstLevelCacheTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmployeeById(1);
            System.out.println(employee);
            // 不会执行SQL
            Employee employee2 = mapper.getEmployeeById(1);
            System.out.println(employee);
            Assertions.assertSame(employee, employee2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "com/moralok/mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
