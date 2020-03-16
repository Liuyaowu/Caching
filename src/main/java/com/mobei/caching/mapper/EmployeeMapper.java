package com.mobei.caching.mapper;

import com.mobei.caching.bean.Employee;
import org.apache.ibatis.annotations.*;

@Mapper
public interface EmployeeMapper {

    @Select("select * from employee where id = #{id}")
    Employee getEmpById(Integer id);

    @Insert("insert into employee(lastName, email, gender, d_id)" +
            "values(#{lastName}, #{email}, #{gender}, #{dId})")
    void insertEmp(Employee emp);

    @Update("update employee set " +
            "lastName = #{lastName}, " +
            "email = #{email}, " +
            "gender = #{gender}, " +
            "d_id = #{dId} where id = #{id}")
    void updateEmp(Employee emp);

    @Delete("delete employee where id = #{id}")
    void deleteEmpById(Integer id);

    @Select("select * from employee where lastName = #{lastName}")
    Employee getEmpByLastName(String lastName);
}
