package com.ellis.cmfz.service;

import com.ellis.cmfz.entity.Chapter;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ChapterService {
    public List<Chapter> getChapterListByAlbumid(String albumid);
    public Map<String,Object> getChapterListByAlbumidAndPage(String albumid,Integer page,Integer pageindex);
    public Chapter getChapterByChapterId(String chapterid);
    public void uploadChapter(MultipartFile audioPath, HttpSession session,String chapterid);
    public String addChapter(Chapter chapter);
    public String editChapter(Chapter chapter);
    public void deleteChapterByChapterids(String[] chapterids,HttpSession session);
    public void deleteChapterByAlbumids(String[] albumids,HttpSession session);
    public void downloadAudio(String audioPath, HttpServletResponse response,HttpSession session);

}
