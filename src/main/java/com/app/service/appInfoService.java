package com.app.service;

import com.app.utils.appversion;
import com.app.pojo.app_category;
import com.app.pojo.app_info;
import com.app.pojo.data_dictionary;
import com.app.utils.appInfos;
import com.app.utils.pageInfo;

import java.util.List;
import java.util.Map;

public interface appInfoService {
    //加载所有的平台列表,返回对象类型是data_dictionary
    List<data_dictionary> selectDataDictionaryList(String tcode);
    //加载所有的一级分类列表，返回对象是app_category
    List<app_category> selectCategoryLevelList(int pid);
    //查找APKName是否已经存在
    List<app_info> selectAPKByName(String APKName);
    //保存appInfo对象
    void saveAppInfo(Map<String,Object> k);
    //获取所有的applist,id表示createBy是谁创建的
    pageInfo<appInfos> selectAppInfoByPage(Map<String,Object> map);

    //根据appInfoId查找appVersion
    List<appversion> selectVersionByAppId(int id);

    //保存appVersion
    void saveAppVersion(Map<String,Object> map);
}
