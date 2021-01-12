package com.moralok.mybatis.mapper;

import com.moralok.mybatis.bean.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author moralok
 * @since 2021/1/11 4:56 下午
 */
public interface EmployeeMapperDynamicSql {

    /**
     * 查询
     *
     * @param employee 条件
     * @return
     */
    List<Employee> listEmployeeByConditionIf(Employee employee);

    /**
     * 查询
     *
     * @param employee 条件
     * @return
     */
    List<Employee> listEmployeeByConditionTrim(Employee employee);

    /**
     * 查询
     *
     * @param employee 条件
     * @return
     */
    List<Employee> listEmployeeByConditionChoose(Employee employee);

    /**
     * 更新
     *
     * @param employee
     */
    void updateEmployeeWithSet(Employee employee);

    /**
     * 查询
     * @param ids ids
     * @return list
     */
    List<Employee> listEmployeeByIds(@Param("ids") List<Integer> ids);

    /**
     * 批量添加员工
     * @param employees 员工
     */
    void addEmployees(@Param("employees") List<Employee> employees);

    /**
     * 批量添加员工
     * @param employees 员工
     */
    void addEmployeesStep(@Param("employees") List<Employee> employees);

    /**
     * 使用内置参数
     * @param employee 员工
     * @return
     */
    List<Employee> listEmployeeByInnerParameter(Employee employee);
}
