package com.moralok.mybatis;

import com.moralok.mybatis.bean.Employee;
import com.moralok.mybatis.mapper.EmployeeMapperAnnotation;
import com.moralok.mybatis.mapper.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * 高级特性测试
 * 如resultMap
 * @author moralok
 * @since 2021/1/11 2:31 下午
 */
public class MapperPlusTest {

    @Test
    void resultMapTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmployeeById(1);
            System.out.println(employee);
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
