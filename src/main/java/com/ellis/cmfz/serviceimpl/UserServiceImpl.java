package com.ellis.cmfz.serviceimpl;

import com.ellis.cmfz.entity.User;
import com.ellis.cmfz.entity.UserMap;
import com.ellis.cmfz.mapper.UserMapper;
import com.ellis.cmfz.service.UserService;
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
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> getUsersByPage(Integer page, Integer pageidex) {
        Integer start=(page-1)*pageidex;
        Integer records = userMapper.selectAllCounts();
        Integer total=records%pageidex==0?records/pageidex:records/pageidex+1;
        List<User> users = userMapper.selectUserByPage(start, pageidex);
        Map<String,Object>map=new HashMap<String,Object>();
        map.put("page",page);
        map.put("records",records);
        map.put("total",total);
        map.put("rows",users);
        return map;
    }

    @Override
    public String addUser(User user) {
        String id = UUID.randomUUID().toString().replace("-", "").substring(0, 20);
        Date date = new Date();
        user.setId(id);
        user.setSignupDate(date);
        userMapper.insertSelective(user);
        return id;
    }

    @Override
    public String editUser(User user) {
        int i = userMapper.updateByPrimaryKeySelective(user);
        return user.getId();
    }

    @Override
    public void uploadUserProfilePic(MultipartFile profilePic, HttpSession session, String userid) {
        String realPath = session.getServletContext().getRealPath("/userProfilePic");
        String originalFilename = profilePic.getOriginalFilename();
        File file1=new File(realPath);
        if(!file1.exists()){
            file1.mkdirs();
        }
        String newName=new Date().getTime()+"~"+originalFilename;

        try {
            profilePic.transferTo(new File(realPath,newName));
            User user=new User();
            user.setId(userid);
            user.setProfilePic(newName);
            int i = userMapper.updateByPrimaryKeySelective(user);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public User getUserByID(String id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public void deleteUsersByIDs(String[] ids, HttpSession session) {
        String realPath = session.getServletContext().getRealPath("/userProfilePic");
        for (String id : ids) {
            User user = userMapper.selectByPrimaryKey(id);
            String profilePic=user.getProfilePic();
            File file =new File(realPath+"/"+profilePic);
            if(file.exists()){
                file.delete();
            }
        }
        userMapper.deleteByIDs(ids);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserMap> getUserMapsByState() {
        List<UserMap> userMaps = userMapper.selectCountGroupByState();
        return userMaps;
    }
}
