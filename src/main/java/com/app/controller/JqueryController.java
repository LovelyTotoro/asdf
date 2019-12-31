package com.app.controller;

import com.app.pojo.app_category;
import com.app.pojo.app_info;
import com.app.pojo.data_dictionary;
import com.app.service.appInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dev/flatform/app")
public class JqueryController {
    @Autowired
    private appInfoService appInfoService;
    //动态加载所属平台列表
    @RequestMapping("datadictionarylist.json")
    @ResponseBody
    public List<data_dictionary> GetDataDictionaryList(String tcode){
        List<data_dictionary> data_dictionaries = appInfoService.getDataDictionaryList(tcode);
        return data_dictionaries;
    }

    @RequestMapping("categorylevellist.json")
    @ResponseBody
    public List<app_category> GetCategoryLevelList(String pid){
        pid=pid.trim();
        if(pid=="") pid="0";
        List<app_category> apps = appInfoService.GetCategoryLevelList(pid);
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
            List<app_info> app = appInfoService.searchAPKByName(APKName);
            if (app == null || app.size()==0){
                appInfo.setAPKName("noexist");
            }else {
                appInfo.setAPKName("exist");
            }
        }
        return appInfo;
    }
}
