package com.moralok.mybatis;

import com.moralok.mybatis.mapper.EmployeeMapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author moralok
 * @since 2021/1/8 4:43 下午
 */
public class MybatisConfigTest {

    /**
     * 1. 根据xml配置文件（全局配置文件），创建一个sqlSessionFactory对象，包含数据源等环境变量
     * 2. sql映射文件，配置了每一个sql以及封装规则
     * 3. 将sql映射文件注册到全局配置文件中
     * 4. 写代码
     *     1. 根据全局配置文件得到sqlSessionFactory
     *     2. 使用sqlSessionFactory，获取sqlSession对象，使用它进行增删改查。一个sqlSession代表和数据库的一次会话，使用完要关闭
     *     3. 使用sql唯一标识告诉mybatis执行哪一个sql，sql都是保存在映射文件中
     * @throws IOException
     */
    @Test
    void sqlSessionFactoryTest() throws IOException {
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();

        // 获取sqlSession实例，能直接执行已经映射的sql语句
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // sql的唯一标识和sql执行需要的参数
            Employee employee = sqlSession.selectOne("com.moralok.mybatis.mapper.EmployeeMapper.selectEmployee", 1);
            System.out.println(employee);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更强的类型检查
     * 抽象的DAO层规范
     * @throws IOException
     */
    @Test
    void useInterfaceTest() throws IOException {
        // 1. 获取SqlSessionFactory
        SqlSessionFactory sqlSessionFactory = getSqlSessionFactory();
        // 2. 获取SqlSession
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            // 3. 获取接口的实现类
            // mybatis会为接口自动创建一个代理对象，代理对象去执行增删改查方法
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            System.out.println(mapper.getClass());
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
