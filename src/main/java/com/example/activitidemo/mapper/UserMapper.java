package com.example.activitidemo.mapper;

import com.example.activitidemo.bean.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {

    @Select({
            "select * from users ORDER BY department"
    })
    List<User> getAllUsers();

    @Select({
            "select * from users where userRole = #{userRole} and department = #{department}"
    })
    List<User> getUsersByRoleAndDept(@Param("userRole") String userRole, @Param("department") String department);

    @Select({
            "select * from users where userName = #{userName} LIMIT 1"
    })
    Map<String,String> getUsersByUserName(@Param("userName") String userName);
    @Select({
            "select salt from users where userName = #{userName}"
    })
    String getSaltByUserName(@Param("userName") String userName);
    @Insert({
            "INSERT INTO users (userId, userName, userRole, password, department,salt) VALUES (#{userId}, #{userName}, #{userRole}, #{password}, #{department},#{salt})"
    })
    Integer newUser(@Param("userId") String userId,@Param("userName") String userName,@Param("userRole") String userRole,@Param("password") String password,@Param("department") String department,@Param("salt") String salt);

}
