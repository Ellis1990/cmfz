package com.ellis.cmfz.serviceimpl;

import com.ellis.cmfz.entity.Album;
import com.ellis.cmfz.entity.Banner;
import com.ellis.cmfz.entity.Chapter;
import com.ellis.cmfz.mapper.ChapterMapper;
import com.ellis.cmfz.service.AlbumService;
import com.ellis.cmfz.service.ChapterService;
import org.apache.commons.io.FileUtils;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.AudioHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private AlbumService albumService;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Chapter> getChapterListByAlbumid(String albumid) {
        List<Chapter> chapters = chapterMapper.selectByAlbumids(albumid);
        return chapters;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> getChapterListByAlbumidAndPage(String albumid, Integer page, Integer pageindex) {
        Integer start = (page-1)*pageindex;
        Integer records = chapterMapper.selectCountByAlbumid(albumid);
        Integer total = records%pageindex==0?records/pageindex:records/pageindex+1;
        List<Chapter> rows = chapterMapper.selectByAlbumidAndPage(albumid, start, pageindex);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("page",page);
        map.put("records",records);
        map.put("total",total);
        map.put("rows",rows);
        return map;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Chapter getChapterByChapterId(String chapterid) {
        Chapter chapter = chapterMapper.selectByPrimaryKey(chapterid);

        return chapter;
    }

    @Override
    public void uploadChapter(MultipartFile audioPath, HttpSession session, String chapterid) {
        String realpath=session.getServletContext().getRealPath("/chapterAudio");

        File file =new File(realpath);
        if(!file.exists()){
            file.mkdirs();
        }
        String originalFilename=audioPath.getOriginalFilename();
        String newFilename=new Date().getTime()+"~"+originalFilename;
        try {
            //获得音频长度
            audioPath.transferTo(new File(realpath,newFilename));
            AudioFile read = AudioFileIO.read(new File(realpath, newFilename));
            AudioHeader audioHeader = read.getAudioHeader();
            int trackLength = audioHeader.getTrackLength();
            String seconds=trackLength%60+"秒";
            String minutes=trackLength/60+"分";
            long size1 = audioPath.getSize();
            String size=size1/1024/1024+"MB";
            Chapter chapter =new Chapter();
            chapter.setId(chapterid);
            chapter.setAudioPath(newFilename);
            chapter.setDuration(minutes+seconds);
            chapter.setSize(size);
            chapterMapper.updateByPrimaryKeySelective(chapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String addChapter(Chapter chapter) {
        String id= UUID.randomUUID().toString().substring(0,20).replace("-","");
        chapter.setId(id);
        int insert = chapterMapper.insert(chapter);
        Album album = albumService.getAlbumByID(chapter.getAlbumid());
        Integer count = album.getCount();
        count=count+1;
        album.setCount(count);
        albumService.editAlbum(album);
        return id;
    }

    @Override
    public String editChapter(Chapter chapter) {
        int i = chapterMapper.updateByPrimaryKeySelective(chapter);

        return chapter.getId();
    }

    @Override
    public void deleteChapterByChapterids(String[] chapterids,HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/chapterAudio");
        String albumid=null;
        for (String chapterid : chapterids) {
            Chapter chapter = chapterMapper.selectByPrimaryKey(chapterid);
            String audioPath = chapter.getAudioPath();
            albumid=chapter.getAlbumid();
            File file = new File(realPath+"/"+audioPath);

            if(file.exists()){
                file.delete();
            }

        }
        int i = chapterMapper.deleteByIDs(chapterids);
        Album album = albumService.getAlbumByID(albumid);
        Integer count = album.getCount();
        count=count-i;
        album.setCount(count);
        albumService.editAlbum(album);

    }

    @Override
    public void deleteChapterByAlbumids(String[] albumids, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/chapterAudio");
        for (String albumid : albumids) {
            List<Chapter> chapters = chapterMapper.selectByAlbumids(albumid);
            for (Chapter chapter : chapters) {
                String audioPath = chapter.getAudioPath();
                File file =new File(realPath+"/"+audioPath);
                if(file.exists()){
                    file.delete();
                }
            }
        }
        int i = chapterMapper.deleteByAlbumids(albumids);
    }

    @Override
    public void downloadAudio(String audioPath, HttpServletResponse response, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/chapterAudio");
        File file = new File(realPath,audioPath);
        String s = audioPath.split("~")[1];
        response.setHeader("content-disposition","attachment;filename="+s);
        ServletOutputStream outputStream=null;
        try{
            outputStream = response.getOutputStream();
            FileUtils.copyFile(file,outputStream);
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
