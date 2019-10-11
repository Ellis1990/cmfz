package com.ellis.cmfz.mapper;

import com.ellis.cmfz.entity.User;
import com.ellis.cmfz.entity.UserMap;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(String id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectUserByPage(@Param("start") Integer start, @Param("pageindex") Integer pageindex);

    Integer selectAllCounts();

    int deleteByIDs(String[] ids);

    List<UserMap> selectCountGroupByState();
}