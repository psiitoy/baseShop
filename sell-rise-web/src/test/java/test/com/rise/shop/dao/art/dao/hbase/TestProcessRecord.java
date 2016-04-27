package test.com.rise.shop.dao.art.dao.hbase;

import com.rise.shop.dao.art.ProcessRecordHbaseDao;
import com.rise.shop.domain.art.hbase.ProcessRecord;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by wangdi on 16-4-25.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class TestProcessRecord {

    @Resource
    ProcessRecordHbaseDao processRecordHbaseDao;

    private String bizType = "reqAspect";

    @Test
    public void testInsert() throws Exception {
        for (int i = 1; i < 10; i++) {
            ProcessRecord processRecord = new ProcessRecord();
            processRecord.setBizType(bizType);
            processRecord.setOrderId(223l + i);
            processRecord.setModified(new Date());
            processRecord.setMsgContent("{bbbbb:" + i + "}");
            processRecordHbaseDao.insert(processRecord);
            Thread.sleep(1000);
        }
    }

    @Test
    public void testListByBizType() throws Exception {
        List<ProcessRecord> list = processRecordHbaseDao.queryListByBizType(bizType, 100);
        System.out.println("##" + list);
        System.out.println(list.size());
    }

    @Test
    public void testListByOrderId() throws Exception {
        Long orderId = 225l;
        List<ProcessRecord> list = processRecordHbaseDao.queryListByOrderId(bizType, orderId, 100);
        System.out.println("##" + list);
        System.out.println(list.size());
    }

    @Test
    public void testDeleteByOrderId() throws Exception {
        Long orderId = 225l;
        processRecordHbaseDao.deleteByOrderId(bizType, orderId);
    }

    @Test
    public void testTruncateByBizType() throws Exception {
        processRecordHbaseDao.truncateByBizType(bizType);
    }
}
