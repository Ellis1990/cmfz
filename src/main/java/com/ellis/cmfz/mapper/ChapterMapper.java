package com.ellis.cmfz.mapper;

import com.ellis.cmfz.entity.Chapter;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ChapterMapper {
    int deleteByPrimaryKey(String id);

    int insert(Chapter record);

    int insertSelective(Chapter record);

    Chapter selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Chapter record);

    int updateByPrimaryKey(Chapter record);

    int deleteByAlbumids(String[] albumids);

    int deleteByIDs(String[] ids);

    List<Chapter> selectByAlbumids(String albumid);

    List<Chapter> selectByAlbumidAndPage(@Param("albumid") String albumid, @Param("start") Integer start, @Param("pageindex") Integer pageindex);

    int selectCountByAlbumid(String albumid);


}