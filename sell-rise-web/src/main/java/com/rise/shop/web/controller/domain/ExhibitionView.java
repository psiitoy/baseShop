package com.rise.shop.web.controller.domain;

import com.rise.shop.common.ano.ViewMeta;
import com.rise.shop.common.beans.ViewBasePersistenceBean;
import com.rise.shop.common.constants.FieldTypeConstant;

import java.util.List;

/**
 * Created by wangdi on 15-1-8.
 */
public class ExhibitionView extends ViewBasePersistenceBean {
    @ViewMeta(name = "展览名", order = 1, notnull = true)
    private String name;    //展览名
    @ViewMeta(name = "开始时间", type = FieldTypeConstant.FIELD_TYPE_TIMESTAMP, order = 2)
    private Long beginTime; //开始时间
    @ViewMeta(name = "结束时间", type = FieldTypeConstant.FIELD_TYPE_TIMESTAMP, order = 3)
    private Long endTime;   //结束时间
    @ViewMeta(name = "是否在展", type = FieldTypeConstant.FIELD_TYPE_RATIO, order = 4, notnull = true)
    private Integer isShow;//是否在展
    @ViewMeta(name = "艺术类别", order = 5, summary = false)
    private List<String> artTypes;  //艺术类别
    @ViewMeta(name = "艺术类别", order = 6, summary = false)
    private List<String> exhiTypes;  //艺术类别
    @ViewMeta(name = "国家", order = 7, notnull = true)
    private String country;//国际
    @ViewMeta(name = "展出地点", order = 8, notnull = true)
    private String address;//展出地点
    @ViewMeta(refid = true, name = "艺术家ID", type = FieldTypeConstant.FIELD_TYPE_SEARCH_ID, order = 9, summary = false)
    private List<Long> artistIds;    //艺术家ID
    @ViewMeta(reffield = "artistIds", name = "艺术家名称", type = FieldTypeConstant.FIELD_TYPE_SEARCH_NAME, order = 10, summary = false)
    private List<String> artistNames;    //艺术家名称
    @ViewMeta(refid = true, name = "策展人ID", type = FieldTypeConstant.FIELD_TYPE_SEARCH_ID, order = 11, summary = false, notnull = true)
    private Long curatorId;  //策展人ID
    @ViewMeta(reffield = "curatorId", type = FieldTypeConstant.FIELD_TYPE_SEARCH_NAME, name = "策展人姓名", order = 12, notnull = true)
    private String curatorName;  //策展人姓名
    @ViewMeta(name = "视频链接", order = 13, summary = false)
    private String videoUrl;    //视频链接
    @ViewMeta(name = "展览作品", order = 14, summary = false)
    private List<String> artistWorks;  //展览作品
    @ViewMeta(name = "关键词", order = 15, summary = false)
    private List<String> keyWords; //关键词

    @Override
    public String toString() {
        return "Exhibition{" +
                "name='" + name + '\'' +
                ", beginTime=" + beginTime +
                ", endTime=" + endTime +
                ", isShow=" + isShow +
                ", artTypes=" + artTypes +
                ", exhiTypes=" + exhiTypes +
                ", country='" + country + '\'' +
                ", address='" + address + '\'' +
                ", artistIds=" + artistIds +
                ", artistNames=" + artistNames +
                ", curatorId=" + curatorId +
                ", curatorName='" + curatorName + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
                ", artistWorks=" + artistWorks +
                ", keyWords=" + keyWords +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Long beginTime) {
        this.beginTime = beginTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
        this.endTime = endTime;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public List<String> getArtTypes() {
        return artTypes;
    }

    public void setArtTypes(List<String> artTypes) {
        this.artTypes = artTypes;
    }

    public List<String> getExhiTypes() {
        return exhiTypes;
    }

    public void setExhiTypes(List<String> exhiTypes) {
        this.exhiTypes = exhiTypes;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<Long> getArtistIds() {
        return artistIds;
    }

    public void setArtistIds(List<Long> artistIds) {
        this.artistIds = artistIds;
    }

    public List<String> getArtistNames() {
        return artistNames;
    }

    public void setArtistNames(List<String> artistNames) {
        this.artistNames = artistNames;
    }

    public Long getCuratorId() {
        return curatorId;
    }

    public void setCuratorId(Long curatorId) {
        this.curatorId = curatorId;
    }

    public String getCuratorName() {
        return curatorName;
    }

    public void setCuratorName(String curatorName) {
        this.curatorName = curatorName;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getArtistWorks() {
        return artistWorks;
    }

    public void setArtistWorks(List<String> artistWorks) {
        this.artistWorks = artistWorks;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }
}
