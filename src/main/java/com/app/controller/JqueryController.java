package com.app.controller;

import com.app.pojo.*;
import com.app.service.appInfoService;
import com.app.service.devUserService;
import com.app.utils.appversion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;


@Controller
@RequestMapping("/dev/flatform/app")
public class JqueryController {
    @Autowired
    private appInfoService appInfoService;
    @Autowired
    private devUserService devUserService;

    //动态加载所属平台列表
    @RequestMapping("datadictionarylist.json")
    @ResponseBody
    public List<data_dictionary> GetDataDictionaryList(String tcode){
        List<data_dictionary> data_dictionaries = appInfoService.selectDataDictionaryList(tcode);
        return data_dictionaries;
    }

    @RequestMapping("categorylevellist.json")
    @ResponseBody
    public List<app_category> GetCategoryLevelList(String pid){
        int tpid;
        if(pid == "" || pid == null) tpid = 0;
        else tpid = Integer.parseInt(pid);
        List<app_category> apps = appInfoService.selectCategoryLevelList(tpid);
        return apps;
    }

    @RequestMapping("apkexist.json")
    @ResponseBody
    public app_info IsAPKExist(String APKName){
        app_info appInfo = new app_info();
        APKName = APKName.trim();
        if(APKName == "" || APKName == null){
            appInfo.setAPKName("empty");
        }else {
            List<app_info> app = appInfoService.selectAPKByName(APKName);
            if (app == null || app.size()==0){
                appInfo.setAPKName("noexist");
            }else {
                appInfo.setAPKName("exist");
            }
        }
        return appInfo;
    }
    @RequestMapping("appversionadd")
    public String AppVersionAdd(Model model, HttpServletRequest request){
        //获取到当前登录的对象
        dev_user user = devUserService.Login((String)request.getSession().getAttribute("devCode"));

        String appId = request.getParameter("id");//获取到对应的appinfo的id
        /*
        *   根据提供的appInfoId从app_version的appId找出所有符合的appVersion
        */
        List<appversion> versions = appInfoService.selectVersionByAppId(Integer.parseInt(appId));
        model.addAttribute("appVersionList",versions);//对应appVersion的所有信息列表
        model.addAttribute("appId",appId);
        model.addAttribute("devUserSession",user);//当前登录用户
        return "developer/appversionadd";
    }
}
