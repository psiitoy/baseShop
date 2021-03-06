package com.rise.shop.domain.art.mongo;

import com.rise.shop.common.beans.ViewBasePersistenceBean;

import java.util.List;

/**
 * Created by wangdi on 15-1-8.
 */
public class Exhibition extends ViewBasePersistenceBean {
    private String name;    //展览名
    private Long beginTime; //开始时间
    private Long endTime;   //结束时间
    private Integer isShow;//是否在展
    private List<String> artTypes;  //艺术类别
    private List<String> exhiTypes;  //艺术类别
    private String country;//国际
    private String address;//展出地点
    private List<Long> artistIds;    //艺术家ID
    private List<String> artistNames;    //艺术家名称
    private Long curatorId;  //策展人ID
    private String curatorName;  //策展人姓名
    private String videoUrl;    //视频链接
    private List<String> artistWorks;  //展览作品
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
