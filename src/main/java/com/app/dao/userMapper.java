package com.app.dao;

import com.app.pojo.dev_user;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface userMapper {

	List<dev_user> selectAll();
	List<dev_user> selectByPage(@Param("params") Map<String,Object> params);
	int count(@Param("params") Map<String,Object> params);//计数
}
