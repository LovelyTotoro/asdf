package com.app.utils;

import java.util.Date;
public class appversion {
    private String apkName;
    private String versionNo;//
    private double versionSize;//
    private String valueName;
    private String downloadLink;//
    private String apkFileName;//
    private Date modifyDate;//
    public appversion(){}
    public appversion(String apkName,String versionNo,int versionSize,String valueName,String downloadLink,String apkFileName,Date modifyDate){
        this.apkName = apkName;
        this.versionNo = versionNo;
        this.versionSize = versionSize;
        this.valueName = valueName;
        this.downloadLink = downloadLink;
        this.apkFileName = apkFileName;
        this.modifyDate = modifyDate;
    }
    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getVersionNo() {
        return versionNo;
    }

    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo;
    }

    public double getVersionSize() {
        return versionSize;
    }

    public void setVersionSize(double versionSize) {
        this.versionSize = versionSize;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getDownloadLink() {
        return downloadLink;
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLink = downloadLink;
    }

    public String getApkFileName() {
        return apkFileName;
    }

    public void setApkFileName(String apkFileName) {
        this.apkFileName = apkFileName;
    }

    public Date getModifyDate() {
        return modifyDate;
    }

    public void setModifyDate(Date modifyDate) {
        this.modifyDate = modifyDate;
    }
}