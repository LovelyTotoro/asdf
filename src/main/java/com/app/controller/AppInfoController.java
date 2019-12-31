package com.app.controller;

import com.app.pojo.app_info;
import com.app.pojo.dev_user;
import com.app.service.devUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.commons.io.FilenameUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/dev/flatform/app")
public class AppInfoController {
    @Autowired
    private devUserService devUserService;
    //跳转到app信息列表界
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

    //跳转到添加app信息列表界面，还要先加载平台列表和几级分类的信息
    @RequestMapping("appinfoadd")
    public String TransToAppInfoAdd(HttpServletRequest request,Model model){
        dev_user user;
        try{
            user = devUserService.Login((String) request.getSession().getAttribute("devCode"));
        }catch (Exception e){
            return "/";
        }
        model.addAttribute("devUserSession",user);
        return "developer/appinfoadd";
    }

    //添加App信息
    /*
        * softwareName  APKName  supportROM  interfaceLanguage  softwareSize  downloads  flatformId
        * categoryLevel1  categoryLevel2  categoryLevel3  status  appInfo  a_logoPicPath
    */
    @RequestMapping("appinfoaddsave")
    public String AppInfoAdd(HttpServletRequest request,Model model){
        app_info appInfo = new app_info();
        MultipartHttpServletRequest params=((MultipartHttpServletRequest) request);
        appInfo.setSoftwareName(params.getParameter("softwareName"));
        appInfo.setAPKName(params.getParameter("APKName"));
        appInfo.setSupportROM(params.getParameter("supportROM"));
        appInfo.setInterfaceLanguage(params.getParameter("interfaceLanguage"));
        appInfo.setSoftwareSize(Integer.parseInt(params.getParameter("softwareSize")));
        appInfo.setDownloads(0);
        appInfo.setFlatformId(Integer.parseInt(params.getParameter("flatformId")));
        appInfo.setCategoryLevel1(Integer.parseInt(params.getParameter("categoryLevel1")));
        appInfo.setCategoryLevel2(Integer.parseInt(params.getParameter("categoryLevel2")));
        appInfo.setCategoryLevel3(Integer.parseInt(params.getParameter("categoryLevel3")));
        appInfo.setFlatformId(Integer.parseInt(params.getParameter("status")));
        dev_user user;//获取到这个人的用户对象
        try{
            user = devUserService.Login((String) request.getSession().getAttribute("devCode"));
        }catch (Exception e){
            return "/";
        }
        appInfo.setCreatedBy(user.getId());

        appInfo.setAppInfo(params.getParameter("appInfo"));
        //下面处理文件
        MultipartFile myFile = params.getFile("a_logoPicPath");
        String fileName = myFile.getOriginalFilename();
        long length = myFile.getSize();
        String xx = FilenameUtils.getExtension(fileName);
        if (xx.equals("jpg") || xx.equals("png")) {
            // 改名
            String newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileName);
            // 获取保存文件的路径
            String path = request.getSession().getServletContext().getRealPath("static/upload");
            appInfo.setLogoPicPath(path);
            // 保存文件
            try {
                myFile.transferTo(new File(path, newFileName));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            model.addAttribute("fileUploadError", "请上传正确的文件格式");
            return "";
        }
        return "";
    }
}
