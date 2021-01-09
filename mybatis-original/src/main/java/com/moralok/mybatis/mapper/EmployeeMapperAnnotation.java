package com.moralok.mybatis.mapper;

import com.moralok.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Select;

/**
 * @author moralok
 * @since 2021/1/9
 */
public interface EmployeeMapperAnnotation {

    /**
     * 根据id查询
     *
     * @param id id
     * @return
     */
    @Select("select * from tbl_employee where id = #{id}")
    Employee getEmployeeById(Integer id);
}
