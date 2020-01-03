package com.app.utils;

public class appInfos {
    //要查找的基本信息
    private String softwareName;//softwareName
    private String APKName;//APKName
    private int softwareSize;//softwareSize
    private String flatformName;//flatformName
    private String categoryLevel1Name;//categoryLevel1Name
    private String categoryLevel2Name;//categoryLevel2Name
    private String categoryLevel3Name;//categoryLevel3Name
    private int versionId;//versionId
    private int id;//指的是appinfo的id
    private String statusName;//statusName
    private int downloads;//downloads
    private String versionNo="null";//versionNo
    private int status;//status

    public int getStatus() {
        return status;
    }

    public int getVersionId() {
        return versionId;
    }

    public void setVersionId(int versionId) {
        this.versionId = versionId;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSoftwareName() {
        return softwareName;
    }

    public void setSoftwareName(String softwareName) {
        this.softwareName = softwareName;
    }

    public String getAPKName() {
        return APKName;
    }

    public void setAPKName(String APKName) {
        this.APKName = APKName;
    }

    public int getSoftwareSize() {
        return softwareSize;
    }

    public void setSoftwareSize(int softwareSize) {
        this.softwareSize = softwareSize;
    }

    public String getFlatformName() {
        return flatformName;
    }

    public void setFlatformName(String flatformName) {
        this.flatformName = flatformName;
    }

    public String getCategoryLevel1Name() {
        return categoryLevel1Name;
    }

    public void setCategoryLevel1Name(String categoryLevel1Name) {
        this.categoryLevel1Name = categoryLevel1Name;
    }

    public String getCategoryLevel2Name() {
        return categoryLevel2Name;
    }

    public void setCategoryLevel2Name(String categoryLevel2Name) {
        this.categoryLevel2Name = categoryLevel2Name;
    }

    public String getCategoryLevel3Name() {
        return categoryLevel3Name;
    }

    public void setCategoryLevel3Name(String categoryLevel3Name) {
        this.categoryLevel3Name = categoryLevel3Name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public int getDownloads() {
        return downloads;
    }

    public void setDownloads(int downloads) {
        this.downloads = downloads;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }
}
