package com.moralok.mybatis;

import com.moralok.mybatis.bean.Department;
import com.moralok.mybatis.bean.Employee;
import com.moralok.mybatis.mapper.DepartmentMapper;
import com.moralok.mybatis.mapper.EmployeeMapperPlus;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

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

    @Test
    void resultMapWithAnotherTest() throws IOException {
        // 级联属性封装
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmployeeAndDeptById(1);
            System.out.println(employee);
            System.out.println(employee.getDept());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void resultMapWithAnotherTest2() throws IOException {
        // 级联属性封装(association)
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmployeeAndDeptById2(1);
            System.out.println(employee);
            System.out.println(employee.getDept());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void resultMapAssociationStepTest() throws IOException {
        // 级联属性封装(association)
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmployeeAndDeptStep(1);
            // System.out.println(employee);
            // System.out.println(employee.getDept());
            // 测试延迟加载，默认toString也会触发
            System.out.println(employee.getLastName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void resultMapCollectionTest() throws IOException {
        // 一对多关联
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
            Department department = mapper.getDeptAndEmployeesById(1);
            System.out.println(department);
            System.out.println(department.getEmployees());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void resultMapCollectionStepTest() throws IOException {
        // 一对多关联，分步
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            DepartmentMapper mapper = sqlSession.getMapper(DepartmentMapper.class);
            Department department = mapper.getDeptAndEmployeesByIdStep(1);
            // System.out.println(department);
            // System.out.println(department.getEmployees());
            // 测试延迟加载
            System.out.println(department.getDepartmentName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void discriminatorTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperPlus mapper = sqlSession.getMapper(EmployeeMapperPlus.class);
            Employee employee = mapper.getEmployeeByIdWithDiscriminator(1);
            System.out.println(employee);
            employee = mapper.getEmployeeByIdWithDiscriminator(2);
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
