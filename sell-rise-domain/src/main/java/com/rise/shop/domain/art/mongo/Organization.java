package com.rise.shop.domain.art.mongo;

import com.rise.shop.common.beans.ViewBasePersistenceBean;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by wangdi on 15-1-8.
 */
public class Organization extends ViewBasePersistenceBean {
    private ObjectId _id;   //mongoId
    private String name;    //机构名称
    private List<Long> headImg; //头像图片
    private Integer orgType;   //类别（画廊，拍卖行，协会，学院）
    private Integer gainType;   //盈利非盈利机构
    private String address; //地址
    private String phone;   //电话
    private String postcode;    //邮编
    private String country; //国家
    private String coordinate;  //坐标
    private String area;
    private String leader;  //负责人
    private String desc;    //简介
    private List<Long> descImg; //简介图片
    private String descImgRemark;
    private String orgUrl;
    private String orgEmail;
    private String orgWeiBo;
    private String orgWeiXin;
    private List<String> keyWords; //关键词

    @Override
    public String toString() {
        return "Organization{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", headImg='" + headImg + '\'' +
                ", orgType=" + orgType +
                ", gainType=" + gainType +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", postcode='" + postcode + '\'' +
                ", country='" + country + '\'' +
                ", coordinate='" + coordinate + '\'' +
                ", area='" + area + '\'' +
                ", leader='" + leader + '\'' +
                ", desc='" + desc + '\'' +
                ", descImg='" + descImg + '\'' +
                ", descImgRemark='" + descImgRemark + '\'' +
                ", orgUrl='" + orgUrl + '\'' +
                ", orgEmail='" + orgEmail + '\'' +
                ", orgWeiBo='" + orgWeiBo + '\'' +
                ", orgWeiXin='" + orgWeiXin + '\'' +
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

    public List<Long> getHeadImg() {
        return headImg;
    }

    public void setHeadImg(List<Long> headImg) {
        this.headImg = headImg;
    }

    public Integer getOrgType() {
        return orgType;
    }

    public void setOrgType(Integer orgType) {
        this.orgType = orgType;
    }

    public Integer getGainType() {
        return gainType;
    }

    public void setGainType(Integer gainType) {
        this.gainType = gainType;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(String coordinate) {
        this.coordinate = coordinate;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<Long> getDescImg() {
        return descImg;
    }

    public void setDescImg(List<Long> descImg) {
        this.descImg = descImg;
    }

    public String getDescImgRemark() {
        return descImgRemark;
    }

    public void setDescImgRemark(String descImgRemark) {
        this.descImgRemark = descImgRemark;
    }

    public String getOrgUrl() {
        return orgUrl;
    }

    public void setOrgUrl(String orgUrl) {
        this.orgUrl = orgUrl;
    }

    public String getOrgEmail() {
        return orgEmail;
    }

    public void setOrgEmail(String orgEmail) {
        this.orgEmail = orgEmail;
    }

    public String getOrgWeiBo() {
        return orgWeiBo;
    }

    public void setOrgWeiBo(String orgWeiBo) {
        this.orgWeiBo = orgWeiBo;
    }

    public String getOrgWeiXin() {
        return orgWeiXin;
    }

    public void setOrgWeiXin(String orgWeiXin) {
        this.orgWeiXin = orgWeiXin;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }
}
