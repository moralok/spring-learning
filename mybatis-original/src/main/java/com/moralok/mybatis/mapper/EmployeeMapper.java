package com.moralok.mybatis.mapper;

import com.moralok.mybatis.bean.Employee;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author moralok
 * @since 2021/1/8 5:52 下午
 */
public interface EmployeeMapper {

    /**
     * 根据id查询
     *
     * @param id id
     * @return
     */
    Employee getEmployeeById(Integer id);

    /**
     * 添加
     *
     * @param employee
     * @return int
     */
    int addEmployee(Employee employee);

    /**
     * 更新
     *
     * @param employee
     * @return
     */
    int updateEmployee(Employee employee);

    /**
     * 删除
     *
     * @param id
     */
    void deleteEmployeeById(Integer id);

    /**
     * 查询
     *
     * @param id id
     * @param lastName lastName
     * @return employee
     */
    Employee getEmployeeByIdAndLastName(@Param("id") Integer id, @Param("lastName") String lastName);

    /**
     * 查询
     * @param map 参数map
     * @return employee
     */
    Employee getEmployeeByMap(Map<String, Object> map);

    /**
     * 查询
     *
     * @param lastName lastName
     * @return list
     */
    List<Employee> listEmployeeByLastNameLike(String lastName);

    /**
     * 查询
     *
     * @param id id
     * @return map
     */
    Map<String, Object> getEmployeeByIdReturnMap(Integer id);

    /**
     * 查询
     *
     * @param lastName lastName
     * @return map
     */
    @MapKey("lastName")
    Map<String, Employee> listEmployeeByLastNameReturnMap(String lastName);
}
