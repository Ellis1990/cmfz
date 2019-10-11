package com.ellis.cmfz.serviceimpl;

import com.ellis.cmfz.entity.Album;
import com.ellis.cmfz.mapper.AlbumMapper;
import com.ellis.cmfz.mapper.ChapterMapper;
import com.ellis.cmfz.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class AlbumServiceImpl implements AlbumService {
    @Autowired
    private AlbumMapper albumMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String,Object> getAlbumsByPage(Integer page, Integer pageIndex) {
        Integer records=albumMapper.selectAllCounts();
        Integer total=records%pageIndex==0?records/pageIndex:records/pageIndex+1;
        Integer start=(page-1)*pageIndex;
        List<Album> albums = albumMapper.selectAllByPage(start, pageIndex);
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("page",page);
        map.put("records",records);
        map.put("total",total);
        map.put("rows",albums);
        return map;
    }

    @Override
    public String addAlbum(Album album) {
        String id= UUID.randomUUID().toString().replace("-","").substring(0,20);
        Integer count=0;
        Date creatDate=new Date();
        album.setCount(count);
        album.setId(id);
        album.setCreateDate(creatDate);
        int insert = albumMapper.insert(album);
        return id;
    }

    @Override
    public Album getAlbumByID(String id) {
        Album album = albumMapper.selectByPrimaryKey(id);

        return album;
    }

    @Override
    public void upload(MultipartFile picPath, HttpSession session, String albumid) {
        Album album=new Album();
        album.setId(albumid);
        String newpicPath=new Date().getTime()+"^"+picPath.getOriginalFilename();
        String realPath = session.getServletContext().getRealPath("/albumImg");
        File file=new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        try {
            picPath.transferTo(new File(realPath,newpicPath));
            album.setPicPath(newpicPath);
            albumMapper.updateByPrimaryKeySelective(album);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String editAlbum(Album album) {
        int i = albumMapper.updateByPrimaryKeySelective(album);

        return album.getId();
    }

    @Override
    public void deleteAlbumByids(String[] ids, HttpSession session) {
        for (int i = 0; i <ids.length ; i++) {
            String picPath1=albumMapper.selectByPrimaryKey(ids[i]).getPicPath();
            String realPath = session.getServletContext().getRealPath("/albumImg");
            File file=new File(realPath+"/"+picPath1);
            if(file.exists()){
                file.delete();
            }
        }

        int i = albumMapper.deleteByIDs(ids);
        int i1 = chapterMapper.deleteByAlbumids(ids);

    }

}
