package com.ellis.cmfz.controller;

import com.ellis.cmfz.entity.Chapter;
import com.ellis.cmfz.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("chapter")
public class ChapterController {
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("getchapterlist")

    public Map<String,Object> getChapterList(Integer page,Integer rows,String albumid){
        Map<String, Object> map = chapterService.getChapterListByAlbumidAndPage(albumid, page, rows);
        return map;
    }
    @RequestMapping("editchapter")

    public Map<String,Object> editChapter(MultipartFile audioPath,String oper,String[] id,HttpSession session,Chapter chapter){
        Map<String,Object> map=new HashMap<String,Object>();
        if(oper.equals("add")){
            String s = chapterService.addChapter(chapter);
            map.put("msg","添加成功");
            map.put("chapterid",s);
        }else if(oper.equals("edit")){
            String audioPath1 = chapter.getAudioPath();
            chapter.setAudioPath(null);
            String chapterid = chapterService.editChapter(chapter);
            map.put("msg","编辑成功");
            if("".equals(audioPath1)){
                map.put("chapterid","");
                return map;
            }
            map.put("chapterid",chapterid);
        }else if(oper.equals("del")){
            chapterService.deleteChapterByChapterids(id,session);
            map.put("msg","添加成功");

        }

        return map;
    }
    @RequestMapping("upload")

    public void upload(MultipartFile audioPath, HttpSession session,String chapterid){
        String audioPath1 = chapterService.getChapterByChapterId(chapterid).getAudioPath();
        if(audioPath1!=null){
            String realPath = session.getServletContext().getRealPath("/chapterAudio");
            File file = new File(realPath+"/"+audioPath1);
            if(file.exists()){
                file.delete();
            }
        }
        chapterService.uploadChapter(audioPath,session,chapterid);

    }
    @RequestMapping("downloadaudio")
    public void downloadChapter(String audioPath, HttpServletResponse response,HttpSession session){

        chapterService.downloadAudio(audioPath,response,session);
    }
}
