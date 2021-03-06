package com.rise.shop.domain.art.mongo;

import com.rise.shop.common.beans.ViewBasePersistenceBean;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by wangdi on 15-1-8.
 */
public class ArtistWork extends ViewBasePersistenceBean {
    private ObjectId _id;   //mongoId
    private Long artistId;  //作者
    private String artistName;  //作者
    private List<Long> workImage;   //作品
    private String workImageRemark;
    private String workName;    //作品名
    private String size;    //尺寸
    private Long time;  //作品年代
    private String rev; //版次
    private String series;  //系列
    private Integer sellNum;    //销售总量
    private String auctionRecord;   //拍卖记录
    private Double price;    //标价
    private Double cost;    //成交价
    private List<Long> orgIds;  //销售渠道
    private List<String> orgNames;  //销售渠道
    private String owner;   //收藏机构或个人
    private List<String> keyWords; //关键词

    @Override
    public String toString() {
        return "ArtistWork{" +
                "_id=" + _id +
                ", artistId=" + artistId +
                ", artistName='" + artistName + '\'' +
                ", workImage='" + workImage + '\'' +
                ", workImageRemark='" + workImageRemark + '\'' +
                ", workName='" + workName + '\'' +
                ", size='" + size + '\'' +
                ", time=" + time +
                ", rev='" + rev + '\'' +
                ", series='" + series + '\'' +
                ", sellNum=" + sellNum +
                ", auctionRecord='" + auctionRecord + '\'' +
                ", price=" + price +
                ", cost=" + cost +
                ", orgIds=" + orgIds +
                ", orgNames=" + orgNames +
                ", owner='" + owner + '\'' +
                ", keyWords=" + keyWords +
                '}';
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public List<Long> getWorkImage() {
        return workImage;
    }

    public void setWorkImage(List<Long> workImage) {
        this.workImage = workImage;
    }

    public String getWorkImageRemark() {
        return workImageRemark;
    }

    public void setWorkImageRemark(String workImageRemark) {
        this.workImageRemark = workImageRemark;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getRev() {
        return rev;
    }

    public void setRev(String rev) {
        this.rev = rev;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public Integer getSellNum() {
        return sellNum;
    }

    public void setSellNum(Integer sellNum) {
        this.sellNum = sellNum;
    }

    public String getAuctionRecord() {
        return auctionRecord;
    }

    public void setAuctionRecord(String auctionRecord) {
        this.auctionRecord = auctionRecord;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
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

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }
}
