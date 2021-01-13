package com.moralok.mybatis;

import com.moralok.mybatis.bean.Department;
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
            // 如果执行过修改，缓存失效
            // 如果主动清理缓存，缓存失效
            // mapper.addEmployee(new Employee(null, "Jerry", true, "Jerry@gmail.com", new Department(2)));
            sqlSession.clearCache();
            Employee employee2 = mapper.getEmployeeById(1);
            System.out.println(employee);
            System.out.println(employee == employee2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void firstLevelCacheInvalidTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(); SqlSession sqlSession2 = sqlSessionFactory.openSession()) {

            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmployeeById(1);
            System.out.println(employee);
            // 1. 不同sqlSession将重新查询
            // 2. 不同查询条件
            // 3. 两次中间执行过增删改
            // 4. 手动清理缓存
            EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
            Employee employee2 = mapper2.getEmployeeById(1);
            System.out.println(employee2);
            Assertions.assertNotSame(employee, employee2);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void secondLevelCacheTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession(); SqlSession sqlSession2 = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmployeeById(1);
            System.out.println(employee);
            sqlSession.commit();
            EmployeeMapper mapper2 = sqlSession2.getMapper(EmployeeMapper.class);
            Employee employee2 = mapper2.getEmployeeById(1);
            sqlSession2.commit();
            System.out.println(employee2);
            System.out.println(employee == employee2);
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
