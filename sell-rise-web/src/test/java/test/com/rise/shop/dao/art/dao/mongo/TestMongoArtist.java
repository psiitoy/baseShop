package test.com.rise.shop.dao.art.dao.mongo;

import com.alibaba.fastjson.JSONObject;
import com.rise.shop.domain.art.mongo.Artist;
import com.rise.shop.persistence.page.PaginatedArrayList;
import com.rise.shop.persistence.page.PaginatedList;
import com.rise.shop.persistence.query.BaseQuery;
import com.rise.shop.persistence.query.ColumnOrder;
import com.rise.shop.persistence.query.OrderByDescEnum;
import com.rise.shop.persistence.query.Query;
import com.rise.shop.service.art.ArtistService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wangdi on 15-1-9.
 */
@ContextConfiguration(locations = {"/spring-config.xml"})
public class TestMongoArtist extends AbstractJUnit4SpringContextTests {
    @Resource
    private ArtistService artistService;

    @Test
    public void testFindT() throws Exception {
        System.out.println(artistService.findBy(new Artist()).size());
    }

    @Test
    public void testInsert() throws Exception {
        for (int i = 0; i < 100; i++) {
            artistService.insert(randomArtist(i + 1));
        }
    }

    private Artist randomArtist(long now) {
        Artist t = new Artist();
//        long now = System.currentTimeMillis();
        t.setId(now);
        t.setName("艺术家" + now);
        t.setSex(1);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return t;
    }

    @Test
    public void testFindByPage() throws Exception {
        PaginatedList<Artist> list;
        try {
            Query query = new BaseQuery();
            query.setPageSize(10);
            query.setPageNo(1);
            list = artistService.findByPage(query);
            System.out.println("###" + list.size() + "#" + list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCount() throws Exception {
        Artist query = new Artist();
        System.out.println("####" + artistService.count(query));
    }

    @Test
    public void testFind() throws Exception {
        Artist t = new Artist();
        t.setId(1421126767581l);
        System.out.println(artistService.findBy(t));
    }

    @Test
    public void testUpdate() throws Exception {
        Artist t = artistService.get(1421126767581l);
        t.setName("ddd");
        System.out.println(artistService.update(t));
    }

    @Test
    public void testUpdate2() throws Exception {
        Artist t = new Artist();
        t.setId(1425625569701l);
        t.setHeadImg(new ArrayList<Long>() {{
            add(1425110707518l);
        }});
        System.out.println(artistService.update(t));

    }

    @Test
    public void testLikeFind() throws Exception {
        PaginatedList<Artist> list = new PaginatedArrayList();
        try {
            list.setIndex(1);
            list.setPageSize(10);
            Map<String, String> jsonMap = new HashMap<String, String>();
            jsonMap.put("name", "a");
            list = artistService.findByPageLike(list, jsonMap);
            System.out.println("###" + list.size() + "#" + list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testInsert2() throws Exception {
        Artist t = new Artist();
        t.setName("艺术家2223");
        t.setSex(1);
        artistService.insert(t);
    }

    public static void main(String[] args) {
        String json = "{name:'111'}";
        Map<String, String> map = new HashMap<String, String>();
        map = JSONObject.parseObject(json, map.getClass());
        System.out.println("####" + map);
    }
}
