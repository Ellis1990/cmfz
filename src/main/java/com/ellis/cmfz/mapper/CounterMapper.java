package com.ellis.cmfz.mapper;

import com.ellis.cmfz.entity.Counter;

public interface CounterMapper {
    int deleteByPrimaryKey(String id);

    int insert(Counter record);

    int insertSelective(Counter record);

    Counter selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Counter record);

    int updateByPrimaryKey(Counter record);
}