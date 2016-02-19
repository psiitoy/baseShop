package test;

import com.rise.shop.dao.art.UserDao;
import com.rise.shop.domain.art.mysql.User;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-18.
 */
@ContextConfiguration(locations = {"/spring-config-dao.xml"})
public class TestMysqlDao extends AbstractJUnit4SpringContextTests {

    @Resource
    UserDao userDao;

    @Test
    public void testInsert() throws Exception {
        User user = new User();
        user.setId(System.currentTimeMillis());
        user.setEmail("wd");
        user.setPwd("123");
        userDao.insert(user);
    }

    @Test
    public void testGet() throws Exception {
        System.out.println(userDao.get(1421592429003l));
    }

    @Test
    public void testFind() throws Exception {
        User u = new User();
        u.setEmail("wd");
        System.out.println(userDao.findBy(null));
    }
}
