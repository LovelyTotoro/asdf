package com.app.service.impl;
import java.util.List;
import java.util.Map;

import com.app.utils.pageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.app.dao.userMapper;
import com.app.pojo.dev_user;
import com.app.service.userService;

@Service
public class userServiceImpl implements userService{
	@Autowired
	private userMapper userMappers;
	
	@Override
	public List<dev_user> allUsers(){
		return userMappers.selectAll();
	}
	@Override
	public pageInfo<dev_user> userByPage(Map<String,Object> params){
		pageInfo<dev_user> pageInfo = new pageInfo<dev_user>();
		if(params.containsKey("pageNow")){
			pageInfo.setNowPage((Integer)params.get("pageNow"));
		}
		params.put("start",(pageInfo.getNowPage()-1)*pageInfo.getSize());
		params.put("size",pageInfo.getSize());
		pageInfo.setCount(userMappers.count(params));//计数
		pageInfo.setUserInfo(userMappers.selectByPage(params));//拉取到用户信息列表
		int totalPage = pageInfo.getCount()%pageInfo.getSize()==0?pageInfo.getCount()/pageInfo.getSize():pageInfo.getCount()/pageInfo.getSize()+1;
		pageInfo.setTotalPage(totalPage);
		return  pageInfo;
	}
}