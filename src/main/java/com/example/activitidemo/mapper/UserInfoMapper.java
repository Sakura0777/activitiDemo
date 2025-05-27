package com.example.activitidemo.mapper;

import com.example.activitidemo.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Select({
            "select * from users ORDER BY department"
    })
    List<UserInfo> getAllUsers();

    @Select({
            "select * from users where userRole = #{userRole} and department = #{department}"
    })
    List<UserInfo> getUsersByRoleAndDept(@Param("userRole") String userRole, @Param("department") String department);
    @Select({
            "select * from users where userName = #{userName}"
    })
    List<UserInfo> getUsersByUserName(@Param("userName") String userName);

}
