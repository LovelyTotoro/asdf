package com.app.dao;

import com.app.pojo.app_category;
import com.app.pojo.app_info;
import com.app.pojo.app_version;
import com.app.pojo.data_dictionary;
import com.app.utils.appversion;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface devAppInfoMapper {
    List<data_dictionary> selectDataInfo(String tcode);
    List<app_category> selectCategoryLevelList(int pid);
    List<app_info> selectAPKByName(String APKName);
    void saveAppInfo(@Param("params") Map<String,Object> params);
    int count(@Param("params") Map<String,Object> params);
    List<app_info> selectAppInfoByPage(@Param("params") Map<String,Object> params);

    app_category selectCategoryById(int id);
    app_version selectVersionById(int id);
    List<data_dictionary> selectDictionaryByValueId(int id);
    List<app_version> selectVersionByAppId(int id);
    void saveAppVersion(@Param("params") Map<String,Object> map);
    List<app_info> selectAppInfoById(int id);
    void updateAppInfo(@Param("params") Map<String,Object> map);
    app_version selectVersionByNo(String no);
}
