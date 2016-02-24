package test.com.rise.shop.dao.art.dao;

import com.rise.shop.domain.art.mongo.Artist;
import com.rise.shop.domain.query.ArtistQuery;
import com.rise.shop.persistence.dao.EntityDao;
import com.rise.shop.persistence.generate.EntityDaoBaseTest;
import com.rise.shop.persistence.query.Query;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wangdi on 16-2-19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/spring-config.xml"})
public class TestArtistEnt extends EntityDaoBaseTest<Artist> {

    @Resource
    EntityDao<Artist> artistDao;


    @Override
    public EntityDao<Artist> getEntityDao() {
        return artistDao;
    }

    @Override
    public Class<Artist> getDomain() {
        return Artist.class;
    }

    @Override
    public Query getQuery() {
        return new ArtistQuery();
    }

    @Test
    public void testGenerate() throws Exception {
        System.out.println(generateSqlAndXml());
    }

    @Test
    public void testExecute() throws Exception {
        execute();
    }

    @Test
    public void testInsert() throws Exception {
        Artist t = new Artist();
        long now = System.currentTimeMillis();
        t.setId(now);
        t.setName("艺术家" + now);
        t.setSex(1);
        artistDao.insert(t);
    }

    @Test
    public void testByTime() throws Exception {
        ArtistQuery baseQuery = new ArtistQuery();
        baseQuery.setPageNo(1);
        baseQuery.setPageSize(100);
        baseQuery.setAgeIntervalGte(30l);
        baseQuery.setAgeIntervalLte(100l);
//        baseQuery.setName("艺术家5");
        List<Artist> list = artistDao.findByPage(baseQuery);
        System.out.println("#" + list);
        for (Artist a : list) {
            System.out.println(a.getAge());
        }
        System.out.println("#" + list.size());
    }

}
