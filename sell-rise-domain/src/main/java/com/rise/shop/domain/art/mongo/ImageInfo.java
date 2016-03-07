package com.rise.shop.domain.art.mongo;

import com.rise.shop.common.beans.ViewBasePersistenceBean;
import org.bson.types.ObjectId;

/**
 * Created by wangdi on 15-2-27.
 */
public class ImageInfo extends ViewBasePersistenceBean {

    private ObjectId _id;   //mongoId

    private String imageUrl;

    private String imageRemark;

    @Override
    public String toString() {
        return "ImageInfo{" +
                "_id=" + _id +
                ", imageUrl='" + imageUrl + '\'' +
                ", imageRemark='" + imageRemark + '\'' +
                '}';
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageRemark() {
        return imageRemark;
    }

    public void setImageRemark(String imageRemark) {
        this.imageRemark = imageRemark;
    }
}
