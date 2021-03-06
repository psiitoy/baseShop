package com.rise.shop.domain.art.mongo;

import com.rise.shop.common.beans.ViewBasePersistenceBean;
import org.bson.types.ObjectId;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

/**
 * Created by wangdi on 15-1-8.
 */
@Document(indexName = "artist", type = "artist", shards = 2, replicas = 0)
public class Artist extends ViewBasePersistenceBean {
    private ObjectId _id;   //mongoId
    private String name;    //姓名
    private List<Long> headImg; //头像
    private String nativePlace; //籍贯
    private Integer sex;    //性别
    private Long birth; //生日
    private Long age; //生日
    private Long death; //死亡年
    private List<String> artTypes;  //艺术类别
    private String school;  //毕业院校
    private List<Long> orgIds;    //所属协会
    private List<String> orgNames;    //所属协会
    private Long artTime; //启示年
    private List<String> awardNames; //获奖
    private String newsImg;   //图片报道
    private String videoUrl;    //视频链接
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
