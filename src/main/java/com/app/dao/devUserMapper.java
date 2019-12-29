package com.app.dao;

import com.app.pojo.dev_user;
import org.apache.ibatis.annotations.Param;

public interface devUserMapper {
    dev_user Login(String devCode);
}
