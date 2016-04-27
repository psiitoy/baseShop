package com.rise.shop.domain.art.hbase;

import com.rise.shop.hbaseaccess.hbase.annotation.Column;
import com.rise.shop.hbaseaccess.hbase.annotation.Family;
import com.rise.shop.hbaseaccess.hbase.annotation.Table;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.Date;

/**
 * 海关业务通用 未做垂直分割
 * Created by wangdi on 16-3-28.
 */
@Table(name = "customs:process_record", method = "rowkey")
@Family("f")
public class ProcessRecord {
    @Column
    private Long orderId;   // rowKey索引2 业务类型
    @Column
    private String bizType; // rowKey索引1
    @Column
    private String msgContent;//非结构化对象如日志，或结构化对象的json格式

    /**
     * 基础数据 (系统赋值)
     */
    @Column
    private Date modified;//更改时间 caution 消息修改时间 源自生产者（生产者赋值）

    public byte[] rowkey() {
        byte[] rowKey = null;
        byte[] typeKey = new byte[Bytes.SIZEOF_INT];
        if (!StringUtils.isEmpty(bizType)) {
            typeKey = Bytes.toBytes(bizType.hashCode());
        }
        byte[] orderIdKey = new byte[Bytes.SIZEOF_LONG];
        if (orderId != null) {
            orderIdKey = Bytes.toBytes(Long.reverse(orderId));
        }
        byte[] timeKey = new byte[Bytes.SIZEOF_LONG];
        if (modified != null) {
            timeKey = Bytes.toBytes(modified.getTime());
        }
        rowKey = Bytes.add(typeKey, orderIdKey, timeKey);
        return rowKey;
    }

    @Override
    public String toString() {
        return "ProcessRecord{" +
                "orderId=" + orderId +
                ", bizType='" + bizType + '\'' +
                ", msgContent='" + msgContent + '\'' +
                ", modified=" + modified +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getBizType() {
        return bizType;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(Date modified) {
        this.modified = modified;
    }
}
