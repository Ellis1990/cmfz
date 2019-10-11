package com.ellis.cmfz.serviceimpl;

import com.ellis.cmfz.entity.Banner;
import com.ellis.cmfz.mapper.BannerMapper;
import com.ellis.cmfz.service.BannerService;
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
public class BannerServiceImpl implements BannerService {
    @Autowired
    private BannerMapper bannerMapper;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> getPageList(Integer page, Integer pageIndex) {

        Integer records = bannerMapper.selectAllCounts();
        Integer start =(page-1)*pageIndex;
        List<Banner> rows = bannerMapper.selectActiveAllByPage(start, pageIndex);
        Integer total= records%pageIndex==0?records/pageIndex:records/pageIndex+1;
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("page",page);
        map.put("records",records);
        map.put("total",total);
        map.put("rows",rows);
        return map;
    }

    @Override
    public String addBanner(Banner banner) {
        String id = UUID.randomUUID().toString().replace("-","").substring(0,20);
        banner.setCreateDate(new Date());
        banner.setId(id);
        bannerMapper.insert(banner);
        return id;
    }

    @Override
    public void upload(MultipartFile imgPath, HttpSession session, String bannerid) {
        String realpath=session.getServletContext().getRealPath("/bannerImg");

        File file =new File(realpath);
        if(!file.exists()){
            file.mkdirs();
        }
        String originalFilename=imgPath.getOriginalFilename();
        String newFilename=new Date().getTime()+"^"+originalFilename;
        try {
            imgPath.transferTo(new File(realpath,newFilename));
            Banner banner =new Banner();
            banner.setId(bannerid);
            banner.setPicPath(newFilename);
            bannerMapper.updateByPrimaryKeySelective(banner);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String editBanner(Banner banner) {
        bannerMapper.updateByPrimaryKeySelective(banner);
        return banner.getId();
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Banner getBannerByID(String id) {
        Banner banner = bannerMapper.selectByPrimaryKey(id);
        return banner;
    }

    @Override
    public void deleteBannersByIDs(String[] ids,HttpSession session) {
        for (int i = 0; i <ids.length ; i++) {
            String picPath1=bannerMapper.selectByPrimaryKey(ids[i]).getPicPath();
            String realPath = session.getServletContext().getRealPath("/bannerImg");
            File file=new File(realPath+"/"+picPath1);
            if(file.exists()){
                file.delete();
            }
        }
        bannerMapper.deleteByIDs(ids);
    }
}
