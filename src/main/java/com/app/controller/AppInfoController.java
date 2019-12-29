package com.app.controller;

import com.app.pojo.dev_user;
import com.app.service.devUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/dev/flatform/app")
public class AppInfoController {
    @Autowired
    private devUserService devUserService;
    //跳转到app信息列表界面
    @RequestMapping("list")
    public String appListMain(Model model, HttpServletRequest httpServletRequest){
        dev_user user;
        try{
            user = devUserService.Login((String) httpServletRequest.getSession().getAttribute("devCode"));
        }catch (Exception e){
            return "/";
        }
        model.addAttribute("devUserSession",user);
        return "developer/appinfolist";
    }

    //新增App信息
    @RequestMapping("list/appinfoadd")
    public String TransToAppInfoAdd(){
        return "developer/appinfoadd";
    }
}
