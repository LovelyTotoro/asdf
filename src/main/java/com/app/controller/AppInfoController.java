package com.app.controller;

import com.app.pojo.*;
import com.app.service.appInfoService;
import com.app.service.devUserService;
import com.app.utils.appInfos;
import com.app.utils.appversion;
import com.app.utils.pageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.apache.commons.io.FilenameUtils;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/dev/flatform/app")
public class AppInfoController {
    @Autowired
    private devUserService devUserService;
    @Autowired
    private appInfoService appInfoService;
    //跳转到app信息列表界以及加载app信息列表
    @RequestMapping("list")
    public String appListMain(Model model, HttpServletRequest httpServletRequest){
        //找出目前登录的这个用户
        dev_user user;
        user = devUserService.Login((String) httpServletRequest.getSession().getAttribute("devCode"));
        // 找出所有的查询条件
        String tt = httpServletRequest.getParameter("pageIndex");
        int pageIndex = 1;
        if(tt != null && tt != "") pageIndex = Integer.parseInt(tt);
        String querySoftwareName = httpServletRequest.getParameter("querySoftwareName");
        if(querySoftwareName!=null) {
            if(querySoftwareName.equals("")){
                querySoftwareName = null;
            }
        }
        String tmp = httpServletRequest.getParameter("queryStatus");
        int queryStatus         = (tmp==null||tmp=="")?-1:Integer.parseInt(tmp);
        tmp = httpServletRequest.getParameter("queryFlatformId");
        int queryFlatformId     = (tmp==null||tmp=="")?-1:Integer.parseInt(tmp);
        tmp = httpServletRequest.getParameter("queryCategoryLevel1");
        int queryCategoryLevel1 = (tmp==null||tmp=="")?-1:Integer.parseInt(tmp);
        tmp = httpServletRequest.getParameter("queryCategoryLevel2");
        int queryCategoryLevel2 = (tmp==null||tmp=="")?-1:Integer.parseInt(tmp);
        tmp = httpServletRequest.getParameter("queryCategoryLevel3");
        int queryCategoryLevel3 = (tmp==null||tmp=="")?-1:Integer.parseInt(tmp);
        //按条件查找内容
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("pageIndex",pageIndex);
        map.put("softWareName",querySoftwareName);//解决空的问题
        map.put("status",queryStatus);
        map.put("flatformId",queryFlatformId);
        map.put("categoryLevel1",queryCategoryLevel1);
        map.put("categoryLevel2",queryCategoryLevel2);
        map.put("categoryLevel3",queryCategoryLevel3);
        map.put("devId",user.getId());
        pageInfo<appInfos> page = appInfoService.selectAppInfoByPage(map);//查找app信息

        //设置回显
        if(querySoftwareName!=null) model.addAttribute("querySoftwareName",querySoftwareName);
        if(queryStatus !=-1) model.addAttribute("queryStatus",queryStatus);
        if(queryFlatformId != -1) model.addAttribute("queryFlatformId",queryFlatformId);
        if(queryCategoryLevel1 != -1) model.addAttribute("queryCategoryLevel1",queryCategoryLevel1);
        if(queryCategoryLevel2 != -1) model.addAttribute("queryCategoryLevel2",queryCategoryLevel2);
        if(queryCategoryLevel3 != -1) model.addAttribute("queryCategoryLevel3",queryCategoryLevel3);

        //查询所有的状态列表
        List<data_dictionary> statusList = appInfoService.selectDataDictionaryList("APP_STATUES");
        //查询所有的平台列表
        List<data_dictionary> flatFormList = appInfoService.selectDataDictionaryList("APP_FLATFORM");
        //查询所有的第一层分类
        List<app_category> categoryLevel1List = appInfoService.selectCategoryLevelList(0);

        //设置所有的信息传送到前端
        model.addAttribute("statusList",statusList);
        model.addAttribute("flatFormList",flatFormList);
        model.addAttribute("categoryLevel1List",categoryLevel1List);
        if(queryCategoryLevel1 != -1){ //如果第一层有显示，那接下来要显示第二层
            model.addAttribute("categoryLevel2List",appInfoService.selectCategoryLevelList(queryCategoryLevel1));
        }
        if(queryCategoryLevel2 != -1){//如果第二层有值要顺便显示第三层
            model.addAttribute("categoryLevel3List",appInfoService.selectCategoryLevelList(queryCategoryLevel2));
        }
        model.addAttribute("statusList",appInfoService.selectDataDictionaryList("APP_STATUS"));
        model.addAttribute("appInfoList",page.getPagesInfo());
        model.addAttribute("pages",page);
        model.addAttribute("devUserSession",user);

        return "developer/appinfolist";
    }

    //跳的转到添加app信息列表界面，还要先加载平台列表和几级分类信息
    @RequestMapping("appinfoadd")
    public String TransToAppInfoAdd(HttpServletRequest request,Model model){
        dev_user user;
        user = devUserService.Login((String) request.getSession().getAttribute("devCode"));
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
        Map<String,Object> map = new HashMap<String, Object>();
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest params = resolver.resolveMultipart(request);
        map.put("softwareName",params.getParameter("softwareName"));
        map.put("APKName",params.getParameter("APKName"));
        map.put("supportROM",params.getParameter("supportROM"));
        map.put("interfaceLanguage",params.getParameter("interfaceLanguage"));
        map.put("softwareSize",Integer.parseInt(params.getParameter("softwareSize")));
        map.put("downloads",0);
        map.put("flatformId",Integer.parseInt(params.getParameter("flatformId")));
        map.put("categoryLevel1",Integer.parseInt(params.getParameter("categoryLevel1")));
        map.put("categoryLevel2",Integer.parseInt(params.getParameter("categoryLevel2")));
        map.put("categoryLevel3",Integer.parseInt(params.getParameter("categoryLevel3")));
        map.put("status",Integer.parseInt(params.getParameter("status")));
        dev_user user;//获取到这个人的用户对象
        user = devUserService.Login((String) request.getSession().getAttribute("devCode"));
        map.put("devId",user.getId());
        map.put("createBy",user.getDevCode());//设置创建人
        map.put("createDate",new Date(System.currentTimeMillis()));
        map.put("appInfo",params.getParameter("appInfo"));
        //下面处理文件
        MultipartFile myFile = params.getFile("a_logoPicPath");
        String fileName = myFile.getOriginalFilename();
        long length = myFile.getSize();
        String xx = FilenameUtils.getExtension(fileName);
        if (xx.equals("jpg") || xx.equals("png")) {
            // 改名
            String newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileName);
            // 获取保存文件的路径
            String path = request.getSession().getServletContext().getRealPath("statics/upload");
            map.put("logoPicPath",path+"/"+newFileName);//本地路径
            String filePath = path + "/" + fileName;
            File desFile = new File(filePath);
            if(!desFile.getParentFile().exists()){
                desFile.mkdirs();
            }
            // 保存文件
            try {
                myFile.transferTo(desFile);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            model.addAttribute("fileUploadError", "请上传正确的图片格式");
            return "developer/appinfoadd";
        }
        appInfoService.saveAppInfo(map);
        //转到主界面
        return "developer/main";
    }
    //增加app的版本信息
    @RequestMapping("addversionsave")
    public String AddVersionSave(Model model,HttpServletRequest request){
        /*
        *   1. 要根据appId设置有一个appVersion的回显对象
        *   2. 文件上传失败有一个fileUploadError的回显
        */
        MultipartResolver resolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        MultipartHttpServletRequest params = resolver.resolveMultipart(request);
        //当前用户
        dev_user user = devUserService.Login((String) request.getSession().getAttribute("devCode"));//获取到当前的对象
        model.addAttribute("devUserSession",user);
        //利用appId进行app历史版本的显示
        String appId = params.getParameter("appId");//回显的appId
        model.addAttribute("appId",appId);
        try{
            //获取页面的参数
            String versionNo = params.getParameter("versionNo");//version版本
            String versionSize = params.getParameter("versionSize");//version的大小
            String tmp = params.getParameter("publishStatus");//version公布状态
            int publishStatus=-1;
            if(tmp != null && !tmp.equals("")) publishStatus = Integer.parseInt(tmp);
            String versionInfo = params.getParameter("versionInfo");//version的信息

            //将页面上传的信息传入map
            Map<String,Object> map = new HashMap<String, Object>();//处理前面上传的参数
            if (versionNo!=null){
                versionNo = versionNo.trim();
                if (!versionNo.equals("")) map.put("versionNo",versionNo);
                System.out.println("1:"+versionNo);
            }
            if (versionSize!=null){
                versionSize = versionSize.trim();
                if(!versionSize.equals(""))map.put("versionSize",Double.parseDouble(versionSize));
            }
            map.put("publishStatus",publishStatus);
            map.put("versionInfo",versionInfo);
            map.put("createdBy",user.getId());
            map.put("creationDate",new Date(System.currentTimeMillis()));//创建时间
            map.put("modifyBy",user.getId());
            map.put("modifyDate",new Date(System.currentTimeMillis()));//修改时间
            map.put("appId",Integer.parseInt(appId));
            //获取文件
            MultipartFile myFile = params.getFile("a_downloadLink");
            //保存文件
            String fileName = myFile.getOriginalFilename();
            map.put("apkFileName",fileName);
            long length = myFile.getSize();
            String fileExtension = FilenameUtils.getExtension(fileName);
            String newFileName;//新的文件名
            String filePath;
            if (fileExtension.equals("apk") && length<5*1024*1024*1024) {
                newFileName = UUID.randomUUID().toString() + "." + FilenameUtils.getExtension(fileName);
                String path = request.getSession().getServletContext().getRealPath("statics/upload");
                filePath = path + "/" + fileName;
                map.put("a_downloadLink",filePath);//文件名+路径
                File desFile = new File(filePath);
                if(!desFile.getParentFile().exists()){
                    desFile.mkdirs();
                }
                // 保存文件
                try {
                    myFile.transferTo(desFile);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                model.addAttribute("fileUploadError", "请上传正确的APK格式");
                return "developer/appversionadd";
            }
            map.put("apkLocPath",filePath);
            appInfoService.saveAppVersion(map);
        }catch (Exception e){
            e.printStackTrace();
            model.addAttribute("fileUploadError","上传失败");
        }finally {
            List<appversion> versions = appInfoService.selectVersionByAppId(Integer.parseInt(appId));
            model.addAttribute("appVersionList",versions);//所有app历史版本的回显
            return "developer/appversionadd";
        }
    }
}