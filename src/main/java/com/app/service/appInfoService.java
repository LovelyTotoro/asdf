package com.app.service;

import com.app.pojo.app_category;
import com.app.pojo.app_info;
import com.app.pojo.data_dictionary;

import java.util.List;

public interface appInfoService {
    //加载所有的平台列表,返回对象类型是data_dictionary
    List<data_dictionary> getDataDictionaryList(String tcode);
    //加载所有的一级分类列表，返回对象是app_category
    List<app_category> GetCategoryLevelList(String pid);
    //查找APKName是否已经存在
    List<app_info> searchAPKByName(String APKName);
}
