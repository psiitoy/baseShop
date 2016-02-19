package com.rise.shop.domain.art.mongo;

import com.rise.shop.domain.ano.FieldMeta;
import com.rise.shop.domain.art.ViewBasePersistenceBean;
import com.rise.shop.domain.constant.FieldTypeConstant;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * Created by wangdi on 15-1-8.
 */
public class ArtistWork extends ViewBasePersistenceBean {
    private ObjectId _id;   //mongoId
    @FieldMeta(refid = true, name = "作者ID", type = FieldTypeConstant.FIELD_TYPE_SEARCH_ID, order = 1, summary = false, editable = false, notnull = true)
    private Long artistId;  //作者
    @FieldMeta(reffield = "artistId", type = FieldTypeConstant.FIELD_TYPE_SEARCH_NAME, name = "作者姓名", order = 2, editable = false, notnull = true)
    private String artistName;  //作者
    @FieldMeta(name = "作品图片", type = FieldTypeConstant.FIELD_TYPE_IMG, order = 3)
    private List<Long> workImage;   //作品
    @FieldMeta(name = "作品图片备注", order = 4, summary = false)
    private String workImageRemark;
    @FieldMeta(name = "作品名", order = 5, notnull = true)
    private String workName;    //作品名
    @FieldMeta(name = "尺寸", order = 6)
    private String size;    //尺寸
    @FieldMeta(name = "创作时间", type = FieldTypeConstant.FIELD_TYPE_TIMESTAMP, order = 7)
    private Long time;  //作品年代
    @FieldMeta(name = "版次", order = 8, summary = false)
    private String rev; //版次
    @FieldMeta(name = "系列", order = 9, summary = false)
    private String series;  //系列
    @FieldMeta(name = "销售总量", order = 10)
    private Integer sellNum;    //销售总量
    @FieldMeta(name = "拍卖记录", order = 11)
    private String auctionRecord;   //拍卖记录
    @FieldMeta(name = "标价", order = 12)
    private Double price;    //标价
    @FieldMeta(name = "成交价", order = 13)
    private Double cost;    //成交价
    @FieldMeta(refid = true, name = "销售渠道(机构ID)", type = FieldTypeConstant.FIELD_TYPE_SEARCH_ID, order = 14, summary = false)
    private List<Long> orgIds;  //销售渠道
    @FieldMeta(reffield = "orgIds", name = "销售渠道(机构名称)", type = FieldTypeConstant.FIELD_TYPE_SEARCH_NAME, order = 15, summary = false)
    private List<String> orgNames;  //销售渠道
    @FieldMeta(name = "收藏机构或个人", order = 16, notnull = true)
    private String owner;   //收藏机构或个人
    @FieldMeta(name = "关键词", order = 17, summary = false)
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
