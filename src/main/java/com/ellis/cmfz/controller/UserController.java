package com.ellis.cmfz.controller;

import com.ellis.cmfz.entity.User;
import com.ellis.cmfz.entity.UserMap;
import com.ellis.cmfz.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping("getuserlist")
    public Map<String,Object> getUserList(Integer page,Integer rows){
        Map<String, Object> usersByPage = userService.getUsersByPage(page, rows);
        return usersByPage;
    }
    @RequestMapping("edituser")
    public Map<String,Object> editUser(User user, String oper, String[] id, HttpSession session){
        Map<String,Object> map=new HashMap<String,Object>();
        if(oper.equals("add")){
            user.setProfilePic(null);
            String userid = userService.addUser(user);
            map.put("msg","添加成功");
            map.put("userid",userid);
        }else if(oper.equals("del")){
            userService.deleteUsersByIDs(id,session);
            map.put("msg","删除成功");
        }else if(oper.equals("edit")){
            String profilePic = user.getProfilePic();
            String s = userService.editUser(user);
            map.put("msg","编辑成功");
            if(profilePic.equals("")){
                map.put("userid","");
                return map;
            }
            map.put("userid",s);
        }
        return map;
    }
    @RequestMapping("upload")
    public void upload(MultipartFile profilePic,HttpSession session,String userid){

        String profilePic1 = userService.getUserByID(userid).getProfilePic();
        if(profilePic1!=null){
            String realPath = session.getServletContext().getRealPath("/userProfilePic");
            File file=new File(realPath+"/"+profilePic1);
            if(file.exists()){
                file.delete();
            }
        }
        userService.uploadUserProfilePic(profilePic,session,userid);
    }

    @RequestMapping("getmap")
    public List<UserMap> getMap(){
        List<UserMap> userMapsByState = userService.getUserMapsByState();
        return userMapsByState;
    }
}
