package com.app.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.app.pojo.dev_user;
import com.app.service.*;

import com.app.utils.pageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class userController {
	@Autowired
	private userService userService;
	
	@RequestMapping("/selectAllUsers")
	public String selectAllNews(Model model) {
		List<dev_user> list = userService.allUsers();
		model.addAttribute("userList", list);
		return "main";
	}
	@RequestMapping("/selectByPage")
	public String selectByPage(@RequestParam("pageNow") Integer pagenow, Model model) {
		Map<String, Object> params = new HashMap<String, Object>();
		if(pagenow != null){
			params.put("pageNow",pagenow);
		}
		pageInfo<dev_user> pageInfo = userService.userByPage(params);
		model.addAttribute("pageinfo",pageInfo);
		return "main";
	}
}
