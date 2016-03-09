package com.rise.shop.web.controller.domain;

import com.rise.shop.common.ano.ViewMeta;
import com.rise.shop.common.beans.ViewBasePersistenceBean;
import com.rise.shop.common.constants.FieldTypeConstant;
import org.bson.types.ObjectId;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * Created by wangdi on 15-1-8.
 */
@Document(indexName = "artist", type = "artist", shards = 2, replicas = 0)
public class ArtistView extends ViewBasePersistenceBean {
    private ObjectId _id;   //mongoId
    @ViewMeta(name = "姓名", order = 2, notnull = true)
    private String name;    //姓名
    @ViewMeta(name = "头像", type = FieldTypeConstant.FIELD_TYPE_IMG, order = 3)
    private List<Long> headImg; //头像
    @ViewMeta(name = "籍贯", order = 4)
    private String nativePlace; //籍贯
    @ViewMeta(name = "性别", type = FieldTypeConstant.FIELD_TYPE_RATIO, order = 5)
    private Integer sex;    //性别
    @ViewMeta(name = "生日", type = FieldTypeConstant.FIELD_TYPE_TIMESTAMP, order = 6)
    private Long birth; //生日
    @ViewMeta(name = "年龄", order = 7, summary = false, reffield = "birth", save = false)
    private Long age; //生日
    @ViewMeta(name = "死亡年", type = FieldTypeConstant.FIELD_TYPE_TIMESTAMP, summary = false, order = 8)
    private Long death; //死亡年
    @ViewMeta(name = "艺术类别", order = 9, summary = false)
    private List<String> artTypes;  //艺术类别
    @ViewMeta(name = "毕业院校", order = 10, summary = false)
    private String school;  //毕业院校
    @ViewMeta(refid = true, name = "所属协会ID", type = FieldTypeConstant.FIELD_TYPE_SEARCH_ID, order = 11, summary = false)
    private List<Long> orgIds;    //所属协会
    @ViewMeta(reffield = "orgIds", name = "所属协会", type = FieldTypeConstant.FIELD_TYPE_SEARCH_NAME, order = 12, summary = false)
    private List<String> orgNames;    //所属协会
    @ViewMeta(name = "启示年", type = FieldTypeConstant.FIELD_TYPE_TIMESTAMP, order = 13)
    private Long artTime; //启示年
    @ViewMeta(name = "获奖", order = 14, summary = false)
    private List<String> awardNames; //获奖
    @ViewMeta(name = "图片报道", order = 15, summary = false)
    private String newsImg;   //图片报道
    @ViewMeta(name = "视频链接", order = 16, summary = false)
    private String videoUrl;    //视频链接
    @ViewMeta(name = "关键词", order = 17, summary = false)
    private List<String> keyWords; //关键词

    @Override
    public String toString() {
        return "Artist{" +
                "_id=" + _id +
                ", name='" + name + '\'' +
                ", headImg='" + headImg + '\'' +
                ", nativePlace='" + nativePlace + '\'' +
                ", sex=" + sex +
                ", birth=" + birth +
                ", age=" + age +
                ", death=" + death +
                ", artTypes=" + artTypes +
                ", school='" + school + '\'' +
                ", orgIds=" + orgIds +
                ", orgNames=" + orgNames +
                ", artTime=" + artTime +
                ", awardNames=" + awardNames +
                ", newsImg='" + newsImg + '\'' +
                ", videoUrl='" + videoUrl + '\'' +
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

    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Long getBirth() {
        return birth;
    }

    public void setBirth(Long birth) {
        this.birth = birth;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public Long getDeath() {
        return death;
    }

    public void setDeath(Long death) {
        this.death = death;
    }

    public List<String> getArtTypes() {
        return artTypes;
    }

    public void setArtTypes(List<String> artTypes) {
        this.artTypes = artTypes;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public List<Long> getOrgIds() {
        return orgIds;
    }

    public void setOrgIds(List<Long> orgIds) {
        this.orgIds = orgIds;
    }

    public List<String> getOrgNames() {
        return orgNames;
    }

    public void setOrgNames(List<String> orgNames) {
        this.orgNames = orgNames;
    }

    public Long getArtTime() {
        return artTime;
    }

    public void setArtTime(Long artTime) {
        this.artTime = artTime;
    }

    public List<String> getAwardNames() {
        return awardNames;
    }

    public void setAwardNames(List<String> awardNames) {
        this.awardNames = awardNames;
    }

    public String getNewsImg() {
        return newsImg;
    }

    public void setNewsImg(String newsImg) {
        this.newsImg = newsImg;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }
}
