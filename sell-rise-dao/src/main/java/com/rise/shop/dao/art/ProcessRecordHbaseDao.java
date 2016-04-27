package com.rise.shop.dao.art;

import com.rise.shop.domain.art.hbase.ProcessRecord;

import java.util.List;

/**
 * 增改查 记录内容不支持改
 * Created by wangdi on 16-4-13.
 */
public interface ProcessRecordHbaseDao {

    /**
     * rowKey设计具体看ProcessRecordRowKey
     *
     * @param processRecord
     */
    void insert(ProcessRecord processRecord);

    /**
     * caution! 删除记录会导致记录追踪失败
     *
     * @param bizType
     * @param orderId
     */
    void deleteByOrderId(String bizType, Long orderId);

    /**
     * caution! 删除所有业务记录 需要评审后 决定调用
     *
     * @param bizType
     */
    void truncateByBizType(String bizType);

    /**
     * 获取某业务的 该orderId所有记录
     *
     * @param bizType
     * @param orderId
     * @return
     */
    List<ProcessRecord> queryListByOrderId(String bizType, Long orderId, int pageSize);

    /**
     * caution! 获取某业务的所有记录
     *
     * @param bizType
     * @param pageSize
     * @return
     */
    List<ProcessRecord> queryListByBizType(String bizType, int pageSize);

}
