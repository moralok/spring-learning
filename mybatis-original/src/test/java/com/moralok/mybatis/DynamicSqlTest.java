package com.moralok.mybatis;

import com.moralok.mybatis.bean.Department;
import com.moralok.mybatis.bean.Employee;
import com.moralok.mybatis.mapper.EmployeeMapperDynamicSql;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
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

    @Test
    void chooseTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
            Employee employee = new Employee(null, null, null ,null);
            List<Employee> employees = mapper.listEmployeeByConditionChoose(employee);
            System.out.println(employees);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void setIfTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
            Employee employee = new Employee(3, null, true ,null);
            mapper.updateEmployeeWithSet(employee);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void foreachTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
            List<Employee> employees = mapper.listEmployeeByIds(Arrays.asList(1, 2, 3, 4));
            for (Employee e : employees) {
                System.out.println(e);
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void foreachAddTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
            List<Employee> employees = new ArrayList<>();
            employees.add(new Employee(null, "smith", true, "smith@gmail.com", new Department(1)));
            employees.add(new Employee(null, "allen", false, "allen@gmail.com", new Department(2)));
            mapper.addEmployees(employees);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void foreachAddStepTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
            List<Employee> employees = new ArrayList<>();
            employees.add(new Employee(null, "smith", true, "smith@gmail.com", new Department(1)));
            employees.add(new Employee(null, "allen", false, "allen@gmail.com", new Department(2)));
            mapper.addEmployeesStep(employees);
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void innerParameterTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
            Employee employee = new Employee(2, null, null, null);
            List<Employee> employees = mapper.listEmployeeByInnerParameter(employee);
            for (Employee e : employees) {
                System.out.println(e);
            }
            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void bindTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapperDynamicSql mapper = sqlSession.getMapper(EmployeeMapperDynamicSql.class);
            Employee employee = new Employee(null, "o", null, null);
            List<Employee> employees = mapper.listEmployeeUseBind(employee);
            for (Employee e : employees) {
                System.out.println(e);
            }
            sqlSession.commit();
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
