package com.app.service.impl;

import com.app.dao.devAppInfoMapper;
import com.app.pojo.app_category;
import com.app.pojo.app_info;
import com.app.pojo.app_version;
import com.app.pojo.data_dictionary;
import com.app.service.appInfoService;
import com.app.utils.appInfos;
import com.app.utils.appversion;
import com.app.utils.pageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class appInfoServiceImpl implements appInfoService {
    @Autowired
    devAppInfoMapper devAppInfoMapper;

    @Override
    public List<data_dictionary> selectDataDictionaryList(String tcode){
        return devAppInfoMapper.selectDataInfo(tcode);
    }

    @Override
    public List<app_category> selectCategoryLevelList(int pid){
        return devAppInfoMapper.selectCategoryLevelList(pid);
    }

    @Override
    public List<app_info> selectAPKByName(String APKName){
        return devAppInfoMapper.selectAPKByName(APKName);
    }
    @Override
    public void saveAppInfo(Map<String,Object> appinfo){
        devAppInfoMapper.saveAppInfo(appinfo);
    }
    public pageInfo<appInfos> selectAppInfoByPage(Map<String,Object> map){
        pageInfo<appInfos> pages = new pageInfo<appInfos>();

        int totalCount = devAppInfoMapper.count(map);//总共有多少个数据项
        pages.setTotalCount(totalCount);

        int size = pageInfo.size;//每页最多多少个数据

        int totalPages = totalCount%size==0?totalCount/size:totalCount/size+1;//看总共有多少页
        pages.setTotalPageCount(totalPages);

        int now = (int)map.get("pageIndex");//得到当前页
        pages.setCurrentPageNo(now);

        int start = (now - 1)*size;//设置起点
        if(now == totalPages) size = totalCount;//当前页是否是最后一页,是就取最后一个值，不是就+size

        map.put("start",start);
        map.put("si",size);

        List<app_info> apps = devAppInfoMapper.selectAppInfoByPage(map);//找出所有的appinfo信息

        pages.setPagesInfo(appinfoToAppinfos(apps));//修改格式

        return pages;
    }
    //根据appInfoId查找appVersion
    public List<appversion> selectVersionByAppId(int id){
        List<app_version> app_versions = devAppInfoMapper.selectVersionByAppId(id);//找出所有的版本

        return App_versionToAppversion(app_versions);
    }

    private List<appversion> App_versionToAppversion(List<app_version> versions){
        List<appversion> appversions = new ArrayList<appversion>();
        for(int i=0;i<versions.size();i++){
            appversions.add(new appversion());
            appversions.get(i).setApkFileName(versions.get(i).getApkFileName());
            appversions.get(i).setDownloadLink((versions.get(i).getDownloadLink()));
            appversions.get(i).setModifyDate(versions.get(i).getModifyDate());
            appversions.get(i).setVersionNo(versions.get(i).getVersionNo());
            appversions.get(i).setVersionSize(versions.get(i).getVersionSize());
            //根据status查找到相应的version对象然后取得ValueName
            List<data_dictionary> dictionary = devAppInfoMapper.selectDictionaryByValueId(versions.get(i).getPublishStatus());
            for (int j=0;j<dictionary.size();j++){
                if (dictionary.get(j).getTypeCode()=="APP_STATUS") appversions.get(i).setValueName(dictionary.get(j).getValueName());
            }
            //根据APPID找到对应的appinfo目标获取ApkName
            List<app_info> appInfos = devAppInfoMapper.selectAppInfoById(versions.get(i).getAppId());
            for (int j=0;j<appInfos.size();j++){
                appversions.get(i).setApkName(appInfos.get(j).getAPKName());
            }
        }

        return appversions;
    }

    private List<appInfos> appinfoToAppinfos(List<app_info> apps){
        List<appInfos> appInfos = new ArrayList<appInfos>();
        for(int i=0;i<apps.size();i++){
            appInfos.add(new appInfos());
            appInfos.get(i).setAPKName(apps.get(i).getAPKName());
            appInfos.get(i).setDownloads(apps.get(i).getDownloads());
            appInfos.get(i).setSoftwareSize(apps.get(i).getSoftwareSize());
            appInfos.get(i).setSoftwareName(apps.get(i).getSoftwareName());
            appInfos.get(i).setStatus(apps.get(i).getStatus());
            appInfos.get(i).setVersionId(apps.get(i).getVersionId());
            app_version ttt = devAppInfoMapper.selectVersionById(apps.get(i).getVersionId());//查找特定ID的版本号为多少
            if (ttt!=null && ttt.getVersionNo()!=null && !ttt.getVersionNo().equals("")) appInfos.get(i).setVersionNo(ttt.getVersionNo());

            appInfos.get(i).setCategoryLevel1Name(devAppInfoMapper.selectCategoryById(apps.get(i).getCategoryLevel1()).getCategoryName());
            appInfos.get(i).setCategoryLevel2Name(devAppInfoMapper.selectCategoryById(apps.get(i).getCategoryLevel2()).getCategoryName());
            appInfos.get(i).setCategoryLevel3Name(devAppInfoMapper.selectCategoryById(apps.get(i).getCategoryLevel3()).getCategoryName());

            app_version version = devAppInfoMapper.selectVersionById(apps.get(i).getVersionId());
            if(version != null) appInfos.get(i).setVersionNo(version.getVersionNo());

            appInfos.get(i).setId(apps.get(i).getId());
            List<data_dictionary> data_dictionaries = devAppInfoMapper.selectDictionaryByValueId(apps.get(i).getStatus());
            for (int j=0 ;j<data_dictionaries.size();j++){
                String tmp = data_dictionaries.get(j).getValueName();
                if(data_dictionaries.get(j).getTypeCode().equals("APP_STATUS" )) appInfos.get(i).setStatusName(tmp);
                if(data_dictionaries.get(j).getTypeCode().equals("APP_FLATFORM")) appInfos.get(i).setFlatformName(tmp);
            }
        }
        return appInfos;
    }
    @Override
    public void saveAppVersion(Map<String,Object> map){
        devAppInfoMapper.saveAppVersion(map);
        //获取完后要将appinfo表的最新版本更新
        String versionNo = (String) map.get("versionNo");
        if(versionNo != null && !versionNo.equals("")){
            app_version version = devAppInfoMapper.selectVersionByNo(versionNo);
            if(version!=null) map.put("versionId",version.getId());
        }
        devAppInfoMapper.updateAppInfo(map);
    }
}