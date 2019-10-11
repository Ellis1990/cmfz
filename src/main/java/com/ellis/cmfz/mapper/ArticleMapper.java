package com.ellis.cmfz.mapper;

import com.ellis.cmfz.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ArticleMapper {
    int deleteByPrimaryKey(String id);

    int insert(Article record);

    int insertSelective(Article record);

    Article selectByPrimaryKey(String id);

    int updateByPrimaryKeySelective(Article record);

    int updateByPrimaryKeyWithBLOBs(Article record);

    int updateByPrimaryKey(Article record);

    List<Article> selectArticleByPage(@Param("start") Integer start, @Param("pageindex") Integer pageindex);

    Integer selectAllCounts();

    int deleteByArticleIDs(String[] articleids);
}