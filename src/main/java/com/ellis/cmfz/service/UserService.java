package com.ellis.cmfz.service;

import com.ellis.cmfz.entity.User;
import com.ellis.cmfz.entity.UserMap;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface UserService {
    Map<String,Object> getUsersByPage(Integer page,Integer pageidex);
    String addUser(User user);
    String editUser(User user);
    public void uploadUserProfilePic(MultipartFile profilePic, HttpSession session,String userid);
    User getUserByID(String id);
    void deleteUsersByIDs(String[] ids,HttpSession session);
    List<UserMap> getUserMapsByState();
}
