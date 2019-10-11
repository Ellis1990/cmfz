package com.ellis.cmfz;

import com.ellis.cmfz.entity.Admin;
import com.ellis.cmfz.mapper.AdminMapper;
import com.ellis.cmfz.mapper.AlbumMapper;
import com.ellis.cmfz.mapper.BannerMapper;
import com.ellis.cmfz.mapper.ChapterMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


public class CmfzApplicationTests {


        {
            System.out.println("b");
        }
        static{
            System.out.println("a");
        }
    CmfzApplicationTests(){
            System.out.println("c");
        }
        public static String getOut(){
            try{
                return "1";
            }catch (Exception e){
                return "2";
            }finally {
                return "3";
            }
        }
        public static void main(String[] args){
            System.out.println(getOut());
        }


}
