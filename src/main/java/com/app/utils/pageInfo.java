package com.app.utils;

import com.app.pojo.app_info;

import java.util.List;

public class pageInfo<T> {
    private int totalCount;//总的数据条数
    private int currentPageNo;//当前页数
    private int totalPageCount;//总的页数
    public static int size = 5;
    List<T> pagesInfo;

    public List<T> getPagesInfo() {
        return pagesInfo;
    }

    public void setPagesInfo(List<T> pagesInfo) {
        this.pagesInfo = pagesInfo;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getCurrentPageNo() {
        return currentPageNo;
    }

    public void setCurrentPageNo(int currentPageNo) {
        this.currentPageNo = currentPageNo;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }
}
