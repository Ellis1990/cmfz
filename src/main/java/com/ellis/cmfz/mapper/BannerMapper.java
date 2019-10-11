package com.ellis.cmfz.mapper;

import com.ellis.cmfz.entity.Banner;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;

public interface BannerMapper {
    int deleteByPrimaryKey(String id);

    int insert(Banner record);

    int insertSelective(Banner record);

    Banner selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Banner record);

    int updateByPrimaryKey(Banner record);

    List<Banner> selectActiveAllByPage(@Param("start") Integer start, @Param("pageIndex") Integer pageIndex);

    Integer selectAllCounts();

    int deleteByIDs(String[] ids);
}