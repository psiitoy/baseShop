package test.com.rise.shop.dao.art.dao.es;

import com.rise.shop.domain.art.mongo.Artist;
import com.rise.shop.service.art.ArtistService;
import com.rise.shop.service.repository.ArtistEsRepository;
import org.elasticsearch.index.query.FilterBuilders;
import org.elasticsearch.index.query.QueryBuilders;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import javax.annotation.Resource;
import java.util.*;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

/**
 * Created by wangdi on 15-1-9.
 */
@ContextConfiguration(locations = {"/spring-config.xml"})
public class TestEsArtistDatas extends AbstractJUnit4SpringContextTests {
    @Resource
    private ArtistEsRepository repository;

    @Before
    public void emptyData() throws Exception {
        repository.deleteAll();
    }

    @Resource
    private ArtistService artistService;

    @Test
    public void testSave1() throws Exception {
        Artist artist = new Artist();
        List<Artist> list = artistService.findBy(artist);
        System.out.println("##" + list);
        repository.save(list);
    }

    @Test
    public void testGet() throws Exception {
        Iterable<Artist> list = repository.findAll();
        int count = 0;
        for (Artist a : list) {
            count++;
            System.out.println("##" + a);
        }
        System.out.println("##total=" + count);
    }

    @Test
    public void shouldIndexSingleBookEntity() {

        Artist book = new Artist();
        book.setId(98123123l);
        book.setName("Spring Data Elasticsearch");
        book.setModified(Calendar.getInstance().getTime());
        //Indexing using repository
        repository.save(book);
        //lets try to search same record in elasticsearch
        Artist indexedBook = repository.findOne(book.getId());
        assertThat(indexedBook, is(notNullValue()));
        assertThat(indexedBook.getId(), is(book.getId()));
    }

    @Test
    public void shouldBulkIndexMultipleBookEntities() {

        Artist book1 = new Artist();
        book1.setId(new Random().nextLong());
        book1.setName("spring data");
        book1.setModified(Calendar.getInstance().getTime());
        Artist book2 = new Artist();
        book2.setId(new Random().nextLong());
        book2.setName("Spring Data Elasticsearch");
        book2.setModified(Calendar.getInstance().getTime());
        //Bulk Index using repository
        repository.save(asList(book1, book2));
        //lets try to search same records in elasticsearch
        Artist indexedBook1 = repository.findOne(book1.getId());
        assertThat(indexedBook1.getId(), is(book1.getId()));
        Artist indexedBook2 = repository.findOne(book2.getId());
        assertThat(indexedBook2.getId(), is(book2.getId()));
    }

    @Test
    @Ignore("not to run as just for showing usage of repository ! might throw java.lang.OutOfMemoryError :-) ")
    public void crudRepositoryTest() {

        Artist book1 = new Artist();
        book1.setId(new Random().nextLong());
        book1.setName("spring data");
        book1.setModified(Calendar.getInstance().getTime());
        Artist book2 = new Artist();
        book2.setId(new Random().nextLong());
        book2.setName("Spring Data Elasticsearch");
        book2.setModified(Calendar.getInstance().getTime());
        List<Artist> books = Arrays.asList(book1, book2);

        //indexing single document
//        repository.save(book1);
        //bulk indexing multiple documents
        repository.save(books);
        //searching single document based on documentId
        Artist book = repository.findOne(book1.getId());
        //to get all records as iteratable collection
        Iterable<Artist> bookList = repository.findAll();
        //page request which will give first 10 document
        Page<Artist> bookPage = repository.findAll(new PageRequest(0, 10));
        // to get all records as ASC on name field
        Iterable<Artist> bookIterable = repository.findAll(new Sort(new Sort.Order(Sort.Direction.ASC, "name")));
        //to get total number of docoments in an index
        Long count = repository.count();
        //to check wheather document exists or not
        boolean exists = repository.exists(book1.getId());
        //delete a document by entity
        repository.delete(book1);
        //delete multiple document using collection
        repository.delete(books);
        //delete a document using documentId
        repository.delete(book1.getId());
        //delete all document
        repository.deleteAll();
    }

    @Test
    public void shouldCountAllElementsInIndex() {
        List<Artist> books = new ArrayList<Artist>();
        for (int i = 1; i <= 10; i++) {
            Artist book = new Artist();
            book.setId(new Random().nextLong());
            book.setName("Spring Data Rocks ! " + i);
            book.setModified(Calendar.getInstance().getTime());
            books.add(book);
        }
        //Bulk Index using repository
        repository.save(books);
        //count all elements
        long count = repository.count();
        assertThat(count, is(equalTo(10L)));
    }

    @Test
    public void shouldExecuteCustomSearchQueries() {
        Artist book1 = new Artist();
        book1.setId(new Random().nextLong());
        book1.setName("Custom Query");
        book1.setModified(Calendar.getInstance().getTime());
        Artist book2 = new Artist();
        book2.setId(new Random().nextLong());
        book2.setName(null);
        book2.setModified(Calendar.getInstance().getTime());
        //indexing a book
        repository.save(Arrays.asList(book1, book2));

        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchAllQuery())
                .withFilter(FilterBuilders.boolFilter().must(FilterBuilders.existsFilter("name")))
                .withPageable(new PageRequest(0, 10))
                .build();

        Page<Artist> books = repository.search(searchQuery);
        assertThat(books.getNumberOfElements(), is(equalTo(1)));
    }

    @Test
    public void shouldExecuteCustomSearchQuery() {
        Artist book1 = new Artist();
        book1.setId(new Random().nextLong());
        book1.setName("Custom Query");
        book1.setModified(Calendar.getInstance().getTime());
        Artist book2 = new Artist();
        book2.setId(new Random().nextLong());
        book2.setName("Elasticsearch QueryBuilder");
        book2.setModified(Calendar.getInstance().getTime());
        //bulk indexing two documents
        repository.save(Arrays.asList(book1, book2));
//        QueryBuilder queryBuilder = QueryBuilders.fieldQuery("name",book1.getName());
        //searching in elasticsearch using repository Page<E> search(QueryBuilder q, PageRequest p ) method.
//        Page<Artist> books =  repository.search(queryBuilder,new PageRequest(0,20));
//        assertThat(books.getNumberOfElements(),is(equalTo(1)));
    }

    @Test
    public void shouldReturnBooksForCustomMethodsWithAndCriteria() {
        Artist book1 = new Artist();
        book1.setId(new Random().nextLong());
        book1.setName("test");
        book1.setSex(1);
        book1.setModified(Calendar.getInstance().getTime());
        Artist book2 = new Artist();
        book2.setId(new Random().nextLong());
        book2.setSex(1);
        book2.setName("test");
        book2.setModified(Calendar.getInstance().getTime());
        //bulk indexing two documents
        repository.save(Arrays.asList(book1, book2));

        Page<Artist> books = repository.findByNameAndSex("test", 1, new PageRequest(0, 10));
        assertThat(books.getContent().size(), is(2));
    }

    @Test
    public void shouldReturnBooksWithName() {
        Artist book1 = new Artist();
        book1.setId(new Random().nextLong());
        book1.setName("test1");
        book1.setModified(Calendar.getInstance().getTime());
        Artist book2 = new Artist();
        book2.setId(new Random().nextLong());
        book2.setName("test2");
        book2.setModified(Calendar.getInstance().getTime());
        //bulk indexing two documents
        repository.save(Arrays.asList(book1, book2));

        Page<Artist> books = repository.findByName("test", new PageRequest(0, 10));
        assertThat(books.getContent().size(), is(1));
    }

    @Test
    public void shouldReturnBooksForCustomMethodsWithOrCriteria() {
        Artist book1 = new Artist();
        book1.setId(new Random().nextLong());
        book1.setName("test Or");
        book1.setSex(1);
        book1.setModified(Calendar.getInstance().getTime());
        Artist book2 = new Artist();
        book2.setId(new Random().nextLong());
        book2.setName("test And");
        book2.setSex(1);
        book2.setModified(Calendar.getInstance().getTime());
        //bulk indexing two documents
        repository.save(Arrays.asList(book1, book2));

        Page<Artist> books = repository.findByNameOrSex("test", 1, new PageRequest(0, 10));
        assertThat(books.getContent().size(), is(2));
    }
}
