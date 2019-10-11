package com.ellis.cmfz.serviceimpl;

import com.ellis.cmfz.entity.Article;
import com.ellis.cmfz.mapper.ArticleMapper;
import com.ellis.cmfz.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import javax.xml.crypto.Data;
import java.util.*;

@Service
@Transactional
public class ArticleServiceImpl implements ArticleService {
    @Autowired
    private ArticleMapper articleMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> getArticleByPage(Integer page, Integer pageindex) {
        Map<String,Object> map=new HashMap<String,Object>();
        Integer records = articleMapper.selectAllCounts();
        Integer start=(page-1)*pageindex;
        Integer total=records%pageindex==0?records/pageindex:records/pageindex+1;
        List<Article> articles = articleMapper.selectArticleByPage(start, pageindex);
        map.put("records",records);
        map.put("page",page);
        map.put("total",total);
        map.put("rows",articles);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Article getArticleByID(String id) {
        Article article = articleMapper.selectByPrimaryKey(id);
        return article;
    }

    @Override
    public void deleteArticleByArticleIDs(String[] Articleids, HttpSession session) {

    }

    @Override
    public String addArticle(Article article) {
        article.setCreateDate(new Date());
        String id=UUID.randomUUID().toString().replace("-","").substring(0,20);
        article.setId(id);
        int insert = articleMapper.insert(article);
        return id;
    }

    @Override
    public String editArticle(Article article) {
        Article article1 = articleMapper.selectByPrimaryKey(article.getId());
        Date createDate = article1.getCreateDate();
        article.setCreateDate(createDate);
        int i = articleMapper.updateByPrimaryKeySelective(article);
        return article.getId();
    }
}
