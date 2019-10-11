package com.ellis.cmfz.service;

import com.ellis.cmfz.entity.Album;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface AlbumService {
    Map<String,Object> getAlbumsByPage(Integer page, Integer pageIndex);
    String addAlbum(Album album);
    Album getAlbumByID(String id);
    void upload(MultipartFile picPath, HttpSession session, String albumid);
    String editAlbum(Album album);
    void deleteAlbumByids(String[] ids,HttpSession session);
}
