package com.rise.shop.web.controller.domain;

import com.rise.shop.common.ano.ViewMeta;
import com.rise.shop.common.beans.ViewBasePersistenceBean;
import com.rise.shop.common.constants.FieldTypeConstant;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by wangdi on 15-1-8.
 */
public class CuratorView extends ViewBasePersistenceBean {
    private ObjectId _id;   //mongoId
    @ViewMeta(name = "姓名", order = 2)
    private String name; //姓名
    @ViewMeta(name = "性别", type = FieldTypeConstant.FIELD_TYPE_RATIO, order = 3)
    private int sex;    //性别
    @ViewMeta(name = "头像", type = FieldTypeConstant.FIELD_TYPE_IMG, order = 4)
    private List<Long> headImg; //头像
    @ViewMeta(name = "国家", order = 5)
    private String country; //国家
    @ViewMeta(name = "学校", order = 6)
    private String school; //学校
    @ViewMeta(name = "文字介绍", order = 7)
    private String desc;    //文字介绍
    @ViewMeta(name = "文章", order = 8)
    private String book;    //文章
    @ViewMeta(name = "视频链接", order = 9)
    private List<String> videoUrls;    //视频链接 多个
    @ViewMeta(name = "关键词", order = 10, summary = false)
    private List<String> keyWords; //关键词

    @Override
    public String toString() {
        return "Curator{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", headImg='" + headImg + '\'' +
                ", country='" + country + '\'' +
                ", school='" + school + '\'' +
                ", desc='" + desc + '\'' +
                ", book='" + book + '\'' +
                ", videoUrls=" + videoUrls +
                ", keyWords=" + keyWords +
                '}';
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<Long> getHeadImg() {
        return headImg;
    }

    public void setHeadImg(List<Long> headImg) {
        this.headImg = headImg;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public List<String> getVideoUrls() {
        return videoUrls;
    }

    public void setVideoUrls(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }
}
