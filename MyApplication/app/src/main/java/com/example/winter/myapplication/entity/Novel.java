package com.example.winter.myapplication.entity;

/**
 * Created by liudashuang on 2017/12/2.
 */

public class Novel {

    private String author;
    private String imageurl;
    private String lashUpdateTime;
    private String lastUpdateChapter;
    private String name;
    private String no;
    private String url;

    public Novel(String author, String imageurl, String lashUpdateTime, String lastUpdateChapter, String name, String no, String url) {
        this.author = author;
        this.imageurl = imageurl;
        this.lashUpdateTime = lashUpdateTime;
        this.lastUpdateChapter = lastUpdateChapter;
        this.name = name;
        this.no = no;
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getLashUpdateTime() {
        return lashUpdateTime;
    }

    public void setLashUpdateTime(String lashUpdateTime) {
        this.lashUpdateTime = lashUpdateTime;
    }

    public String getLastUpdateChapter() {
        return lastUpdateChapter;
    }

    public void setLastUpdateChapter(String lastUpdateChapter) {
        this.lastUpdateChapter = lastUpdateChapter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
