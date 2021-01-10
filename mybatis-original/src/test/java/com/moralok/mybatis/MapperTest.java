package com.moralok.mybatis;

import com.moralok.mybatis.bean.Employee;
import com.moralok.mybatis.mapper.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author moralok
 * @since 2021/1/9
 */
public class MapperTest {

    @Test
    void insertTest() throws IOException {
        // 测试插入
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 默认不是自动提交
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee(null, "Cat", true, "cat@gmail.com");
            int id = mapper.addEmployee(employee);
            System.out.println("新增记录返回值 " + id + " 主键ID " + employee.getId());
            sqlSession.commit();
        }
    }

    @Test
    void updateTest() throws IOException {
        // 测试更新
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            int change = mapper.updateEmployee(new Employee(1, "change", true, "change@gmail.com"));
            System.out.println("更新方法返回值 " + change);
            sqlSession.commit();
        }
    }

    @Test
    void deleteTest() throws IOException {
        // 测试删除
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            mapper.deleteEmployeeById(2);
            sqlSession.commit();
        }
    }

    @Test
    void multiParamsTest() throws IOException {
        // 测试多参数
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getEmployeeByIdAndLastName(4, "Cat");
            System.out.println("多参数方法返回值 " + employee);
            sqlSession.commit();
        }
    }

    @Test
    void multiParamsUseMapTest() throws IOException {
        // 测试多参数
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Map<String, Object> map = new HashMap<>();
            map.put("id", 4);
            map.put("lastName", "Cat");
            Employee employee = mapper.getEmployeeByMap(map);
            System.out.println("多参数使用map封装方法返回值 " + employee);
            sqlSession.commit();
        }
    }

    @Test
    void listTest() throws IOException {
        // 测试返回集合
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            List<Employee> employees = mapper.listEmployeeByLastNameLike("%a%");
            for (Employee e : employees) {
                System.out.println("方法集合 " + e);
            }
            sqlSession.commit();
        }
    }

    private SqlSessionFactory getSqlSessionFactory() throws IOException {
        String resource = "com/moralok/mybatis/mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
