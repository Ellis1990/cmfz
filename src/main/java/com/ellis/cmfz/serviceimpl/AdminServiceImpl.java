package com.ellis.cmfz.serviceimpl;

import com.ellis.cmfz.entity.Admin;
import com.ellis.cmfz.mapper.AdminMapper;
import com.ellis.cmfz.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;
    @Override
    public Admin login(Admin admin) {
        Admin admin1 = adminMapper.selectByUsername(admin.getUsername());
        if(admin1==null){
            return null;
        }
        if(admin1.getPassword().equals(admin.getPassword())){
            return admin1;
        }
        return null;
    }
}
