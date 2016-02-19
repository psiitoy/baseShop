package test;

import com.rise.shop.domain.art.mongo.Artist;
import com.rise.shop.service.repository.ArtistEsRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.Calendar;

/**
 * Created by wangdi on 15-1-9.
 */
@ContextConfiguration(locations = {"/spring-config-test.xml"})
public class TestEsArtist extends AbstractJUnit4SpringContextTests {
    @Resource
    private ArtistEsRepository repository;

    @Before
    public void emptyData() throws Exception {
        repository.deleteAll();
    }


    @Test
    public void testSave1() throws Exception {
        Artist artist = new Artist();
        artist.setId(101l);
        artist.setModified(Calendar.getInstance().getTime());
        artist.setCreated(Calendar.getInstance().getTime());
        artist.setName("wyw");
        artist.setAge(22l);
        repository.save(artist);
    }

}
