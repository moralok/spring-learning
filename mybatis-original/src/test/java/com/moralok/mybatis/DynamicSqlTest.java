package com.moralok.mybatis;

import com.moralok.mybatis.bean.Employee;
import com.moralok.mybatis.mapper.EmployeeMapperDynamicSql;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author moralok
 * @since 2021/1/11 5:23 下午
 */
public class DynamicSqlTest {

    @Test
    void ifAndWhereTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
            Employee employee = new Employee(null, "%o%", null ,null);
            List<Employee> employees = mapper.listEmployeeByConditionIf(employee);
            System.out.println(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void trimTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
            Employee employee = new Employee(null, "%o%", null ,null);
            List<Employee> employees = mapper.listEmployeeByConditionTrim(employee);
            System.out.println(employees);
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
