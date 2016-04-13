package test.com.rise.shop.dao.art.dao;

import com.rise.shop.common.utils.CommonTimeUtils;
import com.rise.shop.domain.art.mysql.User;
import com.rise.shop.domain.query.UserQuery;
import com.rise.shop.persistence.dao.EntityDao;
import com.rise.shop.persistence.generate.EntityDaoBaseTest;
import com.rise.shop.persistence.query.domain.ColumnOrder;
import com.rise.shop.persistence.query.domain.OrderByDescEnum;
import com.rise.shop.view.enumtype.viewtype.AuthCodeTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Random;

/**
 * Created by wangdi on 15-1-18.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config-dao.xml"})
public class TestUserEnt extends EntityDaoBaseTest<User, UserQuery> {

    @Resource
    EntityDao<User> userEntityDao;

    @Override
    public EntityDao<User> getEntityDao() {
        return userEntityDao;
    }

    @Test
    public void testGenerate() throws Exception {
        System.out.println(generateSqlAndXml("art"));
    }

    @Test
    public void testExecute() throws Exception {
        execute();
    }

    @Test
    public void testInsert22() throws Exception {
        User user = new User();
        user.setEmail("adminx");
        user.setPwd("adminx");
        user.setState(1);
        user.setAuthCode(AuthCodeTypeEnum.AUTH_ALL.getType());
        userEntityDao.insert(user);
    }

    @Test
    public void testInsertMore() throws Exception {
        for (int i = 1; i < 100; i++) {
            User user = new User();
            user.setId(System.currentTimeMillis());
            user.setEmail("admin" + i);
            user.setPwd("admin" + i);
            user.setState(1);
            user.setAuthCode(new Random().nextInt(1000));
            userEntityDao.insert(user);
        }
    }

    @Test
    public void testQuery2222() throws Exception {
        UserQuery query = new UserQuery();
//        query.setEmail("admin");
        query.setPageNo(1);
        query.setPageSize(100);
//        query.setAuthCodeSymbolGte(10);
//        query.setAuthCodeSymbolLt(400);
        query.setCreatedSymbolGte(CommonTimeUtils.getDateFromStr("2016-2-26 14:15:35"));

        query.addOrderBy(new ColumnOrder(OrderByDescEnum.ASC, "email"));
        List<User> list = userEntityDao.findByPage(query);
        System.out.println("#" + list);
        for (User user : list) {
            System.out.println(user.getAuthCode());
        }
        System.out.println("#" + list.size());
    }

    @Test
    public void testUpdate() throws Exception {
        User user = new User();
        user.setId(1460549334026l);
        user.setEmail("ttttte1");
        user.setModified(CommonTimeUtils.getDateFromStr("2016-04-13 20:10:48"));
        System.out.println("##" + userEntityDao.updateCasByModified(user));

    }
}
