package com.ellis.cmfz.service;

import com.ellis.cmfz.entity.Article;

import javax.servlet.http.HttpSession;
import java.util.Map;

public interface ArticleService {
    public Map<String,Object> getArticleByPage(Integer page,Integer pageindex);
    public Article getArticleByID(String id);
    public void deleteArticleByArticleIDs(String[] Articleids, HttpSession session);
    public String addArticle(Article article);
    public String editArticle(Article article);
}
