package com.ellis.cmfz.controller;

import com.ellis.cmfz.entity.Admin;
import com.ellis.cmfz.service.AdminService;
import com.ellis.cmfz.util.ValidateImageCodeUtils;
import com.mysql.cj.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("loginpage")
public class LoginController {
    @Autowired
    AdminService adminService;
    @RequestMapping("login")
    @ResponseBody
    public String login(HttpSession session, Admin admin, HttpServletResponse response,String code){
        String code1 = (String)session.getAttribute("code");
        if (!code.equals(code1)){
            return "codefailed";
        }

        Admin login = adminService.login(admin);
        if(login==null){
            return "loginfailed";
        }else{
            session.setAttribute("login",login);
            return "success";
        }
    }
    @RequestMapping("getcode")
    public void getCode(HttpServletResponse response,HttpSession session){
        String securityCode = ValidateImageCodeUtils.getSecurityCode();
        session.setAttribute("code",securityCode);
        BufferedImage image = ValidateImageCodeUtils.createImage(securityCode);
        ServletOutputStream out =null;
        try {
            out = response.getOutputStream();
            ImageIO.write(image,"png",out);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @RequestMapping("signout")

    public String signOut(HttpSession session){
        session.invalidate();
        return "redirect:/login/login.jsp";
    }
}
