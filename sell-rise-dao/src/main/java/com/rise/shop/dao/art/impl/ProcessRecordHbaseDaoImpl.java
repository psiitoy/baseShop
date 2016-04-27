package com.rise.shop.dao.art.impl;

import com.rise.shop.dao.art.ProcessRecordHbaseDao;
import com.rise.shop.domain.art.hbase.ProcessRecord;
import com.rise.shop.hbaseaccess.hbase.HyBaseOpsTemplate;
import org.apache.hadoop.hbase.client.Scan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import java.util.Date;
import java.util.List;

/**
 * 增改查 记录内容不支持改
 * Created by wangdi on 16-4-13.
 */
@Repository
public class ProcessRecordHbaseDaoImpl extends HyBaseOpsTemplate implements ProcessRecordHbaseDao {

    public final static Logger logger = LoggerFactory.getLogger(ProcessRecordHbaseDaoImpl.class);

//    static {
//        HyBase.loadModelType(ProcessRecord.class);
//    }

    @Override
    public void insert(ProcessRecord processRecord) {
        Assert.notNull(processRecord);
        Assert.notNull(processRecord.getOrderId());
        Assert.notNull(processRecord.getBizType());
        Assert.notNull(processRecord.getMsgContent());
        Assert.notNull(processRecord.getModified());
        logger.info("insert begin processRecord={}", processRecord);
        save(processRecord);
        logger.info("insert end processRecord={},ret={}", processRecord);
    }

    @Override
    public void deleteByOrderId(String bizType, Long orderId) {
        Assert.notNull(bizType);
        Assert.notNull(orderId);
        logger.info("deleteByOrderId begin bizType={},orderId={}", bizType, orderId);
        deleteRange(bizType, orderId);
        logger.info("deleteByOrderId end bizType={},orderId={}", bizType, orderId);
    }

    @Override
    public void truncateByBizType(String bizType) {
        Assert.notNull(bizType);
        logger.info("truncateByBizType begin bizType={}", bizType);
        deleteRange(bizType, null);
        logger.info("truncateByBizType end bizType={}", bizType);
    }

    private void deleteRange(String bizType, Long orderId) {
        logger.info("deleteRange begin bizType={},orderId={}", bizType, orderId);
        byte[][] rowKeys = getStartAndEndRowKeyByOrderId(bizType, orderId);
        deleteRange(rowKeys[0], rowKeys[1], ProcessRecord.class);
        logger.info("deleteRange end bizType={},orderId={}", bizType, orderId);
    }

    @Override
    public List<ProcessRecord> queryListByOrderId(String bizType, Long orderId, int pageSize) {
        Assert.notNull(bizType);
        Assert.notNull(orderId);
        logger.info("queryListByOrderId begin bizType={},orderId={}", bizType, orderId);
        List<ProcessRecord> records = queryListByCondition(bizType, orderId, pageSize);
        logger.info("queryListByOrderId end bizType={},orderId={},size={}", bizType, orderId, records.size());
        return records;
    }

    @Override
    public List<ProcessRecord> queryListByBizType(String bizType, int pageSize) {
        Assert.notNull(bizType);
        logger.info("queryListByOrderId begin bizType={},pageSize={}", bizType, pageSize);
        List<ProcessRecord> records = queryListByCondition(bizType, null, pageSize);
        logger.info("queryListByOrderId end bizType={},orderId={},size={}", bizType, null, records.size());
        return records;
    }

    private List<ProcessRecord> queryListByCondition(String bizType, Long orderId, int pageSize) {
        byte[][] rowKeys = getStartAndEndRowKeyByOrderId(bizType, orderId);
        Scan scan = new Scan();
        scan.setStartRow(rowKeys[0]);
        scan.setStopRow(rowKeys[1]);
        scan.setMaxResultSize(pageSize);
        return batchFetch(scan, ProcessRecord.class);
    }

    /**
     * 获取rowKey区间
     *
     * @param bizType
     * @param orderId
     * @return
     */
    private byte[][] getStartAndEndRowKeyByOrderId(String bizType, Long orderId) {
        /**
         * startRowkey
         */
        ProcessRecord beginRecord = new ProcessRecord();
        beginRecord.setBizType(bizType);
        beginRecord.setOrderId(orderId);
        byte[] startRowkey = beginRecord.rowkey();
        /**
         * endRowkey
         */
        ProcessRecord endRecord = new ProcessRecord();
        endRecord.setBizType(bizType);
        if (orderId == null) {
            //如果不传orderId则 全业务扫描
            endRecord.setOrderId(Long.MAX_VALUE);
        } else {
            endRecord.setOrderId(orderId);
            endRecord.setModified(new Date());
        }
        byte[] endRowkey = endRecord.rowkey();
        return new byte[][]{startRowkey, endRowkey};
    }

}
