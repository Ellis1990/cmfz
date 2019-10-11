package com.ellis.cmfz.mapper;

import com.ellis.cmfz.entity.Album;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AlbumMapper {
    int deleteByPrimaryKey(String id);

    int insert(Album record);

    int insertSelective(Album record);

    Album selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Album record);

    int updateByPrimaryKeyWithBLOBs(Album record);

    int updateByPrimaryKey(Album record);

    List<Album> selectAllByPage(@Param("start")Integer start,@Param("pageindex")Integer pageindex);

    Integer selectAllCounts();

    int deleteByIDs(String[] ids);
}