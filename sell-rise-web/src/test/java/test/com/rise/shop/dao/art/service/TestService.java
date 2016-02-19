package test.com.rise.shop.dao.art.service;//import com.rise.shop.common.util.PaginatedArrayList;
//import com.rise.shop.common.util.PaginatedList;
//import com.rise.shop.domain.Ware;
//import com.rise.shop.service.OrderService;
//import com.rise.shop.service.WareService;
//import org.junit.Test;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import javax.annotation.Resource;
//
///**
// * Created by wangdi on 14-12-10.
// */
//@ContextConfiguration(locations = {"/spring-config.xml"})
//public class TestService extends AbstractJUnit4SpringContextTests {
//
//    @Resource
//    OrderService orderService;
//
//    @Test
//    public void testCache() throws Exception {
//        wareService.queryCacheWareById(1418298027847l);
//        wareService.queryCacheWareById(1418298027847l);
//        wareService.queryCacheWareById(1418298027429l);
//        wareService.queryCacheWareById(1418298027429l);
//        wareService.queryCacheWareById(1418298027847l);
//    }
//
//    @Test
//    public void testGet() throws Exception {
//        System.out.println(orderService.get(222l));
//    }
//
//    @Resource
//    WareService wareService;
//
//    @Test
//    public void testWareInsert() throws Exception {
//        for (int i = 0; i < 100; i++) {
//            System.out.println(wareService.insert(ranWare(i + 1)));
//        }
//
//    }
//
//    private Ware ranWare(int no) {
//        Ware ware = new Ware();
//        ware.setColor("");
//        ware.setDescription("");
//        ware.setName("");
//        ware.setPrice(2);
//        ware.setSize(12);
//        ware.setState(3);
//        ware.setStoreNum(222);
//        ware.setWareId(no + 0l);
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return ware;
//    }
//
//    @Test
//    public void testWareGet() throws Exception {
//        System.out.println(wareService.get(1418298028022l));
//    }
//
//    @Test
//    public void testWareUpdate() throws Exception {
//        Ware ware = wareService.get(1418298028022l);
//        ware.setStoreNum(333);
//        System.out.println(wareService.update(ware));
//    }
//
//    @Test
//    public void testWareDelete() throws Exception {
//        Ware ware = wareService.get(1418298028022l);
//        System.out.println(wareService.delete(ware));
//    }
//
//    @Test
//    public void testPage() throws Exception {
//        //        orderQuery.setValue(100);
//        PaginatedList<Ware> list = new PaginatedArrayList();
//        list.setIndex(11);
//        list.setPageSize(10);
//        WareQuery orderQuery = new WareQuery();
//        list = wareService.findByPage(list, orderQuery);
//        System.out.println("##" + list);
//        System.out.println(list.size());
//    }
//}
