package com.app.controller;

import com.app.pojo.dev_user;
import com.app.service.devUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/dev")
public class DevUserController {
    @Autowired
    private devUserService devUserService;

    //这个只是用来从标识界面转向登录界面而已
    @RequestMapping("login")
    public String devTrans(){
        return "devlogin";
    }

    //登录功能
    @RequestMapping("dologin")
    public String Login(@RequestParam("devCode")String devCode, @RequestParam("devPassword") String devPassword,Model model, HttpSession httpSession ){
        devCode = devCode.trim();
        devPassword = devPassword.trim();
        dev_user user = devUserService.Login(devCode);
        if ( user!=null && user.getDevPassword().equals(devPassword)){
            /* 登录成功 */
            httpSession.setAttribute("devName",user.getDevName());
            httpSession.setAttribute("devCode",user.getDevCode());
            model.addAttribute("devUserSession",user);
            return "developer/main";
        }else{
            /*登录失败*/
            model.addAttribute("error","用户名或者密码错误");
            return "devlogin";
        }
    }

}
