package test.com.rise.shop.dao.art.dao.mongo;

import com.rise.shop.domain.art.mongo.ImageInfo;
import com.rise.shop.service.art.ImageInfoService;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;

/**
 * Created by wangdi on 15-1-9.
 */
@ContextConfiguration(locations = {"/spring-config.xml"})
public class TestMongoImageInfo extends AbstractJUnit4SpringContextTests {
    @Resource
    private ImageInfoService imageInfoService;

    @Test
    public void testGet() throws Exception {
        System.out.println("##" + imageInfoService.get(1425108174546l));
    }

    @Test
    public void testInsert() throws Exception {
        ImageInfo t = new ImageInfo();
        t.setId(1425108174546l);
        t.setImageUrl("http://www.baidu.com/img/bd_logo1.png");
        t.setImageRemark("33333333333");
        imageInfoService.insert(t);
    }

    @Test
    public void testUpdate() throws Exception {
        ImageInfo t = new ImageInfo();
        t.setId(1425108174546l);
//        t.setImageUrl("http://www.baidu.com/img/bd_logo1.png");
        t.setImageRemark("33333333333");
        System.out.println(imageInfoService.update(t));
    }

}
