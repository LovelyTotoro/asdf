package com.app.service;

import java.util.List;
import java.util.Map;

import com.app.pojo.dev_user;
import com.app.utils.pageInfo;

public interface userService {
	List<dev_user> allUsers();
	pageInfo<dev_user> userByPage(Map<String,Object> params);
}
