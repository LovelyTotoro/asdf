package com.app.dao;

import com.app.pojo.app_category;
import com.app.pojo.app_info;
import com.app.pojo.data_dictionary;

import java.util.List;

public interface devAppInfoMapper {
    List<data_dictionary> getFlatList(String tcode);
    List<app_category> getCategoryLevelList(String pid);
    List<app_info> searchAPKByName(String APKName);
}
