package test;

import com.rise.shop.domain.art.mysql.User;
import com.rise.shop.domain.enumtype.AuthCodeTypeEnum;
import com.rise.shop.domain.query.UserQuery;
import com.rise.shop.persistence.dao.EntityDao;
import com.rise.shop.persistence.generate.EntityDaoBaseTest;
import com.rise.shop.persistence.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config-dao.xml"})
public class TestMysqlDaoUser extends EntityDaoBaseTest<User> {

    @Resource
    EntityDao<User> userEntityDao;


    @Override
    public EntityDao<User> getEntityDao() {
        return userEntityDao;
    }

    @Override
    public Class<User> getDomain() {
        return User.class;
    }

    @Override
    public Query getQuery() {
        return new UserQuery();
    }

    @Test
    public void testExecute() throws Exception {
        execute();
    }

    @Test
    public void testInsert() throws Exception {
        User user = new User();
        user.setId(2l);
        user.setEmail("admin");
        user.setPwd("admin");
        user.setState(1);
        user.setAuthCode(AuthCodeTypeEnum.AUTH_ALL.getType());
        userEntityDao.insert(user);
    }

    @Test
    public void testInsert2() throws Exception {
        for (int i = 0; i < 100; i++) {
            User user = new User();
            user.setId(i + 100l);
            user.setEmail("admin" + i);
            user.setPwd("admin" + i);
            user.setState(1);
            user.setAuthCode(AuthCodeTypeEnum.AUTH_ALL.getType());
            userEntityDao.insert(user);
        }
    }
}
