package com.moralok.mybatis.bean;

import org.apache.ibatis.type.Alias;

/**
 * @author moralok
 * @since 2021/1/8 4:57 下午
 */
@Alias("emp")
public class Employee {

    private Integer id;

    private String lastName;

    private Boolean gender;

    private String email;

    private Department dept;

    public Employee() {
    }

    public Employee(Integer id, String lastName, Boolean gender, String email) {
        this.id = id;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
    }

    public Employee(Integer id, String lastName, Boolean gender, String email, Department dept) {
        this.id = id;
        this.lastName = lastName;
        this.gender = gender;
        this.email = email;
        this.dept = dept;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Department getDept() {
        return dept;
    }

    public void setDept(Department dept) {
        this.dept = dept;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", lastName='" + lastName + '\'' +
                ", gender=" + gender +
                ", email='" + email + '\'' +
                ", dept=" + dept +
                '}';
    }
}
