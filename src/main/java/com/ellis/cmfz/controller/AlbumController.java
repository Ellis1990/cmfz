package com.ellis.cmfz.controller;

import com.ellis.cmfz.entity.Album;
import com.ellis.cmfz.service.AlbumService;
import com.ellis.cmfz.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("album")
public class AlbumController {
    @Autowired
    private AlbumService albumService;
    @Autowired
    private ChapterService chapterService;
    @RequestMapping("getalbumlist")
    public Map<String,Object> getAlbumList(Integer page,Integer rows){
        Map<String, Object> albumsByPage = albumService.getAlbumsByPage(page, rows);
        return albumsByPage;
    }
    @RequestMapping("editalbum")
    public Map<String,Object> editAlbum(Album album, String oper, String[] id, HttpSession session){
        Map<String,Object> map=new HashMap<String, Object>();
        if(oper.equals("add")){
            album.setPicPath(null);
            String albumid = albumService.addAlbum(album);
            map.put("msg","添加成功");
            map.put("albumid",albumid);
        }else if(oper.equals("edit")){
            String picPath = album.getPicPath();
            album.setPicPath(null);
            String albumid = albumService.editAlbum(album);
            map.put("msg","编辑成功");
            if("".equals(picPath)){
                map.put("albumid","");
            }else{
                map.put("albumid",albumid);
            }
        }else if(oper.equals("del")){
            chapterService.deleteChapterByAlbumids(id,session);
            albumService.deleteAlbumByids(id,session);

            map.put("msg","删除成功");
        }
        return map;
    }
    @RequestMapping("upload")
    public void upload(String albumid, MultipartFile picPath,HttpSession session){
        String picPath1 = albumService.getAlbumByID(albumid).getPicPath();
        if(picPath1!=null){
            String realPath = session.getServletContext().getRealPath("/albumImg");
            File file=new File(realPath+"/"+picPath1);
            if(file.exists()){
                file.delete();
            }
        }
        albumService.upload(picPath,session,albumid);
    }

}
