package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.annotation.AutoFIll;
import com.sky.dto.EmployeePageQueryDTO;
import com.sky.entity.Employee;
import com.sky.enumeration.OperationType;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public interface EmployeeMapper {

    //新增员工
    @AutoFIll(value = OperationType.INSERT)
    @Insert("insert into employee (name, username, password, phone, sex, id_number, create_time,update_time, create_user, update_user, status) " +
            "values " +
            "(#{name},#{username},#{password},#{phone},#{sex},#{idNumber},#{createTime},#{updateTime},#{createUser},#{updateUser},#{status})")
    void insert(Employee employee);

    /**
     * 根据用户名查询员工
     * @param username
     * @return
     */
    @Select("select * from employee where username = #{username}")
    Employee getByUsername(String username);

    //分页查询员工
    Page<Employee> pageQuery(EmployeePageQueryDTO employeePageQueryDTO);
    //启用或禁用员工账号
//     @Update("update employee set status =#{status} " +
//            "where id = #{id}")
//    void startOrStop(Integer status,Long id);

    //根据主键动态修改属性
    @AutoFIll(value = OperationType.UPDATE)
    void update(Employee employee);
    //根据id查询员工信息
    @Select("select * from employee where id = #{id}")
    Employee getById(Long id);
}
