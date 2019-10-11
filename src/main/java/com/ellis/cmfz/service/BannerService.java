package com.ellis.cmfz.service;

import com.ellis.cmfz.entity.Banner;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface BannerService {
    public Map<String,Object> getPageList(Integer page,Integer pageIndex);
    public String addBanner(Banner banner);
    public void upload(MultipartFile picPath, HttpSession session,String bannerid);
    public String editBanner(Banner banner);
    public Banner getBannerByID(String id);
    public void deleteBannersByIDs(String[] ids,HttpSession session);

}
