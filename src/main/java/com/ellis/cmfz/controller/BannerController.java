package com.ellis.cmfz.controller;

import com.ellis.cmfz.entity.Banner;
import com.ellis.cmfz.service.BannerService;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("banner")
public class BannerController {
    @Autowired
    private BannerService bannerService;
    @RequestMapping("getbannerlist")
    public Map<String,Object> getBannerList(Integer page,Integer rows){
        Map<String, Object> banners = bannerService.getPageList(page, rows);
        return banners;
    }
    @RequestMapping("editbanner")
    public Map<String,Object> editBanner(Banner banner, String oper,String[] id,HttpSession session){

        Map<String,Object> map=new HashMap<>();
        if(oper.equals("add")){
            banner.setPicPath(null);
            String id1 = bannerService.addBanner(banner);
            map.put("msg","添加成功");
            map.put("bannerid",id1);
        }else if(oper.equals("edit")){
            String picPath = banner.getPicPath();

                banner.setPicPath(null);

            String id1 = bannerService.editBanner(banner);
            map.put("msg","编辑成功");
            if("".equals(picPath)){
                map.put("bannerid","");

                return map;
            }
            map.put("bannerid",id1);
        }else if(oper.equals("del")){
            bannerService.deleteBannersByIDs(id,session);
            map.put("msg","删除成功");
        }
        return map;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile picPath,HttpSession session,String bannerid){
        String picPath1 = bannerService.getBannerByID(bannerid).getPicPath();

        if(picPath1!=null){
            String realPath = session.getServletContext().getRealPath("/bannerImg");
            File file=new File(realPath+"/"+picPath1);
            if(file.exists()){
                file.delete();
            }
        }
        bannerService.upload(picPath,session,bannerid);
    }

}
