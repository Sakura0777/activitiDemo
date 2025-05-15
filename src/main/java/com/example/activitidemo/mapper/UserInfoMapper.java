package com.example.activitidemo.mapper;

import com.example.activitidemo.bean.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserInfoMapper {

    @Select({
            "select * from users"
    })
    List<UserInfo> getAllUsers();
}
