package com.rise.shop.domain.art.mongo;

import com.rise.shop.domain.ano.FieldMeta;
import com.rise.shop.domain.art.ViewBasePersistenceBean;
import com.rise.shop.domain.constant.FieldTypeConstant;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by wangdi on 15-1-8.
 */
public class Organization extends ViewBasePersistenceBean {
    private ObjectId _id;   //mongoId
    @FieldMeta(name = "机构名称", order = 2, summary = true)
    private String name;    //机构名称
    @FieldMeta(name = "机构标志", type = FieldTypeConstant.FIELD_TYPE_IMG, order = 3, summary = true)
    private List<Long> headImg; //头像图片
    @FieldMeta(name = "类别", type = FieldTypeConstant.FIELD_TYPE_RATIO, order = 4, summary = true)
    private Integer orgType;   //类别（画廊，拍卖行，协会，学院）
    @FieldMeta(name = "盈利机构标识", type = FieldTypeConstant.FIELD_TYPE_RATIO, order = 5, summary = true)
    private Integer gainType;   //盈利非盈利机构
    @FieldMeta(name = "地址", order = 6, summary = true)
    private String address; //地址
    @FieldMeta(name = "电话", order = 7, summary = true)
    private String phone;   //电话
    @FieldMeta(name = "邮编", order = 8, summary = true)
    private String postcode;    //邮编
    @FieldMeta(name = "国家", order = 9, summary = true)
    private String country; //国家
    @FieldMeta(name = "坐标", order = 10, summary = true)
    private String coordinate;  //坐标
    @FieldMeta(name = "展厅面积", order = 11, summary = false)
    private String area;
    @FieldMeta(name = "负责人", order = 12, summary = true)
    private String leader;  //负责人
    @FieldMeta(name = "简介", order = 13, summary = true)
    private String desc;    //简介
    @FieldMeta(name = "简介图片", type = FieldTypeConstant.FIELD_TYPE_IMG, order = 14, summary = true)
    private List<Long> descImg; //简介图片
    @FieldMeta(name = "简介图片备注", order = 15, summary = false)
    private String descImgRemark;
    @FieldMeta(name = "官网地址", order = 16, summary = false)
    private String orgUrl;
    @FieldMeta(name = "官方邮箱", order = 17, summary = false)
    private String orgEmail;
    @FieldMeta(name = "官方微博", order = 18, summary = false)
    private String orgWeiBo;
    @FieldMeta(name = "官方微信", order = 19, summary = false)
    private String orgWeiXin;
    @FieldMeta(name = "关键词", order = 20, summary = false)
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
