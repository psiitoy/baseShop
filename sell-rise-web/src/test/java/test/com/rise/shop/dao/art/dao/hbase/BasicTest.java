package test.com.rise.shop.dao.art.dao.hbase;

import com.rise.shop.hbaseaccess.hbase.HyBase;
import com.rise.shop.hbaseaccess.hbase.HyBaseOpsTemplate;
import com.rise.shop.hbaseaccess.hbase.annotation.Column;
import com.rise.shop.hbaseaccess.hbase.annotation.Family;
import com.rise.shop.hbaseaccess.hbase.annotation.Table;
import com.rise.shop.hbaseaccess.hbase.model.RltState;
import com.rise.shop.hbaseaccess.hbase.serial.impl.KryoByteFieldConverter;
import com.rise.shop.hbaseaccess.util.ClassUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import java.util.List;

public class BasicTest {

    static Configuration conf = new Configuration();
    static HyBaseOpsTemplate hyBaseTemplate = null;

    static {
//        conf.set("hbase.zookeeper.quorum", "192.168.178.91,192.168.178.92,192.168.178.93");
//        conf.set("zookeeper.znode.parent", "/hbase_lions");
//		hyBaseTemplate = new HyBaseOpsTemplate(conf);
        conf = HBaseConfiguration.create();
        HyBase.addSerial("kryo", KryoByteFieldConverter.class);

    }

    @Table(name = "customs:process_record", method = "rowkey")
    @Family("f")
    public static class Entity {

        String key;

        @Column
        String attr1;

        @Column
        String attr2;

        public byte[] rowkey() {
            return Bytes.toBytes(this.key);
        }
    }

    @Test
    public void positiveSaveModelTest() {

        HyBase.loadModelType(Entity.class);

        hyBaseTemplate = new HyBaseOpsTemplate(conf);

        Entity entity = new Entity();
        entity.key = "helloworld2";
        entity.attr1 = "attr1";
        entity.attr2 = "attr2";

        hyBaseTemplate.save(entity);
    }

    @Test
    public void positiveSaveFieldTest() {

        HyBase.loadModelType(Entity.class);

        hyBaseTemplate = new HyBaseOpsTemplate(conf);

        Entity entity = new Entity();
        entity.key = "helloworld";
        entity.attr1 = "attr1";
        entity.attr2 = "attr2";
        // 1. save the entity
        hyBaseTemplate.save(entity);
        // 2. fetch it
        Entity entity2 = new Entity();
        entity2.key = "helloworld";
        hyBaseTemplate.fetch(entity2);

        entity2.attr1 = "update_attr1";
        hyBaseTemplate.saveColumn(entity2, new String[]{"attr1"});
    }

    @Test
    public void positiveScanModelTest() {
        ClassUtils.newInstance(Entity.class);

        HyBase.loadModelType(Entity.class);

        hyBaseTemplate = new HyBaseOpsTemplate(conf);

        List<Entity> entities = hyBaseTemplate.batchFetch(new Scan(), Entity.class);

        System.out.println(entities.size());
    }

    @Test
    public void positiveFetchModelTest() {

        HyBase.loadModelType(Entity.class);

        hyBaseTemplate = new HyBaseOpsTemplate(conf);

        Entity entity = new Entity();
        entity.key = "helloworld2";
        RltState state = hyBaseTemplate.fetch(entity);
        System.out.println(state.isExist());

        entity.key = "helloworld";
        state = hyBaseTemplate.fetch(entity);
        System.out.println(state.isExist());


        System.out.println(entity.attr1);
        System.out.println(entity.attr2);
    }

    @Table(name = "test_entity", method = "rowkey")
    @Family("cf_var")
    public static class EntityKryo {

        String key;

        @Column(serial = "kryo")
        String attr1;

        @Column(serial = "kryo")
        String attr2;

        public byte[] rowkey() {
            return Bytes.toBytes(this.key);
        }
    }

    @Test
    public void positiveSaveKryoModelTest() {

        HyBase.loadModelType(Entity.class);

        hyBaseTemplate = new HyBaseOpsTemplate(conf);

        EntityKryo entity = new EntityKryo();
        entity.key = "helloworld";
        entity.attr1 = "attr1";
        entity.attr2 = "attr2";

        hyBaseTemplate.save(entity);
    }

    @Test
    public void positiveSaveKryoFieldTest() {

        HyBase.loadModelType(Entity.class);

        hyBaseTemplate = new HyBaseOpsTemplate(conf);

        EntityKryo entity = new EntityKryo();
        entity.key = "helloworld";
        entity.attr1 = "attr1";
        entity.attr2 = "attr2";
        // 1. save the entity
        hyBaseTemplate.save(entity);
        // 2. fetch it
        EntityKryo entity2 = new EntityKryo();
        entity2.key = "helloworld";
        hyBaseTemplate.fetch(entity2);

        entity2.attr1 = "update_attr1";
        hyBaseTemplate.saveColumn(entity2, new String[]{"attr1"});
    }

    @Test
    public void positiveScanKryoModelTest() {
        ClassUtils.newInstance(Entity.class);

        HyBase.loadModelType(Entity.class);

        hyBaseTemplate = new HyBaseOpsTemplate(conf);

        List<EntityKryo> entities = hyBaseTemplate.batchFetch(new Scan(), EntityKryo.class);

        System.out.println(entities.size());
    }

    @Test
    public void positiveFetchKryoModelTest() {

        HyBase.loadModelType(Entity.class);

        hyBaseTemplate = new HyBaseOpsTemplate(conf);

        EntityKryo entity = new EntityKryo();
        entity.key = "helloworld2";
        RltState state = hyBaseTemplate.fetch(entity);
        System.out.println(state.isExist());

        entity.key = "helloworld";
        state = hyBaseTemplate.fetch(entity);
        System.out.println(state.isExist());


        System.out.println(entity.attr1);
        System.out.println(entity.attr2);
    }

}
