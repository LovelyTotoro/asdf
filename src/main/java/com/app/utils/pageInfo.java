package com.app.utils;

import java.util.List;

public class pageInfo<T> {
    private int nowPage=1;
    private int size = 3;
    private int totalPage;
    private int count=0;
    private List<T> userInfo;

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int startPage) {
        this.nowPage = startPage;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<T> getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(List<T> pageInfo) {
        this.userInfo = pageInfo;
    }
}
