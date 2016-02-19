package test;//import com.rise.shop.common.util.PaginatedArrayList;
//import com.rise.shop.common.util.PaginatedList;
//import com.rise.shop.dao.OrderDao;
//import com.rise.shop.dao.base.query.OrderQuery;
//import com.rise.shop.domain.Order;
//import org.junit.Test;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import javax.annotation.Resource;
//import java.util.HashMap;
//import java.util.Random;
//
///**
// * Created by wangdi on 14-12-8.
// */
//@ContextConfiguration(locations = {"/spring-config.xml"})
//public class TestOrderDao  extends AbstractJUnit4SpringContextTests {
//
//    @Resource
//    OrderDao orderDao;
//
//    @Test
//    public void testOrderGet() throws Exception {
//        System.out.println(orderDao.get(1420532177894l));
//
//    }
//
//    @Test
//    public void testQuery() throws Exception {
//        Order order = new Order();
//        order.setOrderId(1420532177894l);
////        orderQuery.setState(0);
//        System.out.println(orderDao.findBy(order));
//
//    }
//
//    @Test
//    public void testPage() throws Exception {
////        orderQuery.setValue(100);
//        PaginatedList<Order> list = new PaginatedArrayList();
//        list.setIndex(1);
//        list.setPageSize(10);
//        OrderQuery orderQuery = new OrderQuery();
//        list = orderDao.findByPage(list,orderQuery);
////        System.out.println(list.get(0).getOrderId()+","+list.get(1).getOrderId());
//        System.out.println(list.size());
//        System.out.println(list);
//    }
//
//    @Test
//    public void testBatchInsert() throws Exception {
//        for(int i =0;i<100;i++){
//            orderDao.insert(randomOrder());
//        }
//    }
//
//    @Test
//    public void testInsert() throws Exception {
//        orderDao.insert(randomOrder());
//    }
//
//    private Order randomOrder(){
//        Order order = new Order();
//        long now = System.currentTimeMillis();
//        order.setRemark("");
//        order.setCity(1);
//        order.setConsAddress("");
//        order.setConsEmail("");
//        order.setConsMobilePhone("");
//        order.setConsName("");
//        order.setConsPhone("");
//        order.setConsPostCode("");
//        order.setCost(1d);
//        order.setCounty(1);
//        order.setDeligoodType(1);
//        order.setFreight(3d);
//        order.setInvoiceState(2);
//        order.setOperator(2);
//        order.setOrderId(now);
//        order.setOrderType(1);
//        order.setYn(1);
//        order.setState(1);
//        order.setState2(1);
//        order.setVenderId(1l);
//        order.setProvince(1);
//        order.setPaymentType(2);
//        order.setOutboundTime(now);
//        order.setOrderCreateTime(now);
//        try {
//            Thread.sleep(200);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return order;
//    }
//
//    @Test
//    public void testUpdate() throws Exception {
//        Order order = orderDao.get(222l);
//        order.setCost(999d);
//        System.out.println(orderDao.update(order));
//
//    }
//
//    @Test
//    public void testDel() throws Exception {
//        Order order = orderDao.get(222l);
//        System.out.println(orderDao.delete(order));
//    }
//}
