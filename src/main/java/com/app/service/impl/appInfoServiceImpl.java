package com.app.service.impl;

import com.app.dao.devAppInfoMapper;
import com.app.pojo.app_category;
import com.app.pojo.app_info;
import com.app.pojo.data_dictionary;
import com.app.service.appInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class appInfoServiceImpl implements appInfoService {
    @Autowired
    devAppInfoMapper devAppInfoMapper;

    @Override
    public List<data_dictionary> getDataDictionaryList(String tcode){
        return devAppInfoMapper.getFlatList(tcode);
    }

    @Override
    public List<app_category> GetCategoryLevelList(String pid){
        return devAppInfoMapper.getCategoryLevelList(pid);
    }

    @Override
    public List<app_info> searchAPKByName(String APKName){
        return devAppInfoMapper.searchAPKByName(APKName);
    }
}
