package com.hanami.squirtle.bean;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.List;

/**
 * Created by hanami on 2017/4/14.
 */

public class GankEntity implements Serializable {

    private static final long serialVersionUID = -2600330151554512031L;

    /*private String _id;
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;*/
    @Expose
    String _id;
    @Expose
    String createdAt;
    @Expose
    String desc;
    @Expose
    List<String> images;
    @Expose
    String publishedAt;
    @Expose
    String source;
    @Expose
    String type;
    @Expose
    String url;
    @Expose
    boolean used;
    @Expose
    String who;

    //图片的真实高度
    int realHeight;

    public int getRealHeight() {
        return realHeight;
    }

    public void setRealHeight(int realHeight) {
        this.realHeight = realHeight;
    }

    public GankEntity() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public String toString() {
        return "GankEntity{" +
                "_id='" + _id + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", desc='" + desc + '\'' +
                ", images=" + images +
                ", publishedAt='" + publishedAt + '\'' +
                ", source='" + source + '\'' +
                ", type='" + type + '\'' +
                ", url='" + url + '\'' +
                ", used=" + used +
                ", who='" + who + '\'' +
                ", realHeight=" + realHeight +
                '}';
    }
}
