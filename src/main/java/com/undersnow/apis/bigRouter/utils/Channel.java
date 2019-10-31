package com.undersnow.apis.bigRouter.utils;

public class Channel {

    private   int notworking =0 ;

    private   String uri;

    private   String img;
    private   String language;
    private   String name;

    private   int category;
    private   long dbid;
    public Channel(String name, String language, String image, String uri) {
        this.name = name;
        this.language = language;
        this.uri = uri;
        this.img = image;
        language="AR";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public int getNotworking() {
        return notworking;
    }

    public void setNotworking(int notworking) {
        this.notworking = notworking;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public long getDbid() {
        return dbid;
    }

    public void setDbid(long dbid) {
        this.dbid = dbid;
    }
}