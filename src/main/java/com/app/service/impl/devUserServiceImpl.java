package com.app.service.impl;

import com.app.dao.devUserMapper;
import com.app.pojo.dev_user;
import com.app.service.devUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class devUserServiceImpl implements devUserService {
    @Autowired
    private devUserMapper devUserMapper;

    @Override
    public dev_user Login(String devCode){
        dev_user user = devUserMapper.Login(devCode);
        return user;
    }
}
