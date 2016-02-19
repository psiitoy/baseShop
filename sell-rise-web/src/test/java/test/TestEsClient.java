//package test;
//
//import org.codehaus.jackson.JsonGenerationException;
//import org.codehaus.jackson.map.ObjectMapper;
//import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse;
//import org.elasticsearch.action.count.CountResponse;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.GetResponse;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequestBuilder;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.action.search.SearchType;
//import org.elasticsearch.client.Client;
//import org.elasticsearch.client.transport.TransportClient;
//import org.elasticsearch.common.jackson.core.JsonProcessingException;
//import org.elasticsearch.common.joda.time.DateTimeZone;
//import org.elasticsearch.common.joda.time.format.ISODateTimeFormat;
//import org.elasticsearch.common.settings.ImmutableSettings;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.transport.InetSocketTransportAddress;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.XContentBuilder;
//import org.elasticsearch.index.query.*;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHitField;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.aggregations.Aggregation;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.metrics.sum.Sum;
//import org.elasticsearch.search.aggregations.metrics.sum.SumBuilder;
//import org.elasticsearch.search.sort.SortOrder;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
//
//import java.io.IOException;
//import java.util.*;
//
//import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;
//import static org.elasticsearch.index.query.FilterBuilders.boolFilter;
//import static org.elasticsearch.index.query.FilterBuilders.queryFilter;
//import static org.elasticsearch.index.query.FilterBuilders.termFilter;
//import static org.elasticsearch.index.query.QueryBuilders.boolQuery;
//import static org.elasticsearch.index.query.QueryBuilders.termQuery;
//
///**
// * Created by wangdi on 15-1-9.
// */
//@ContextConfiguration(locations = {"/spring-config.xml"})
//public class TestEsClient extends AbstractJUnit4SpringContextTests {
//    private static Client client = null;
//
//    public static void main(String[] args) {
//
//        try {
//            //用集群名字，集群节点地址构建es client
//// client = getClient("jsf-elasticsearch-1.7", "192.168.200.189", 9370);
//            client = getClient("elasticsearch", "172.17.0.2", 9300);
//            //创建indexName:twitter, typeName:tweet的索引
//            createIndex("twitter", "tweet");
//// deleteDocuments("vip_coupon", "vip_coupon_user");
//            //用java字符串在索引twitter,type为tweet中创建document
//            indexWithStr("twitter", "tweet");
//            //用java map在索引twitter,type为tweet中创建document
//            indexWithMap("twitter", "tweet");
//            //用java bean在索引twitter,type为tweet中创建document
//            indexWithBean("twitter", "tweet");
//            indexWithXContent("twitter", "tweet");
//            //在index:twitter, type:tweet中用document id获取
//            getWithId("twitter", "tweet", "4");
//            getWithId("twitter", "tweet", "5");
//            scrollSearch("twitter", "tweet", "1", "2");
//            searchWithFilter("twitter", "tweet", "1", "2");
//            //多ID查询doc
//            searchWithIds("twitter", "tweet", "postDate", "message", "1", "2");
//            countWithQuery("twitter", "tweet", "user", "kimchy", "postDate", "message");
//            //在index:twitter, type:tweet中做term query查询
//            searchWithTermQuery("twitter", "tweet", "user", "101", "kimchy", "message");
//            //sum one field value
//            sumOneField("twitter", "tweet", "price");
//            //在index:twitter, type:tweet中做term query查询并返还指定的field
//            searchWithTermQueryAndRetureSpecifiedFields("twitter", "tweet", "user", "kimchy", "postDate", "message", "user", "message");
//            //在index:twitter, type:tweet中做booean query查询
//            searchWithBooleanQuery("twitter", "tweet", "user", "kimchy", "message", "Elasticsearch", "postDate", "message");
//            searchWithBooleanFilter("twitter", "tweet", "user", "kimchy", "message", "Elasticsearch", "postDate", "message");
//            //在index:twitter, type:tweet中做range query查询
//            numericRangeSearch("twitter", "tweet", "price", 6.1, 6.3, "message");
//            termRangeSearch("twitter", "tweet", "tid", "10000", "20000", "message");
//            dateRangeSearch("twitter", "tweet", "endTime", "2015-08-20 12:27:11", "2015-08-29 14:00:00");
//            dateRangeSearch2("twitter", "tweet", "endTime", "2015-08-21T08:35:13.890Z", "2015-08-30T14:00:00.000Z");
//            rangeSearchWithOtherSearch("twitter", "tweet", "tid", "10000", "20000", "message");
//            //在index:twitter, type:tweet中做fuzzy search查询
//            fuzzySearch("twitter", "tweet", "message", "Elastic", "postDate", "message");
//            //在index:twitter, type:tweet中做wildcard查询
//            wildcardSearch("twitter", "tweet", "message", "El*stic", "postDate", "message");
//            //在index:twitter, type:tweet中删除id为1的doc
//            deleteDocWithId("twitter", "tweet", "1");
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            if (client != null) {
//                client.close();
//            }
//        }
//    }
//
//    private static void indexWithXContent(String indexName, String typeName) throws IOException {
//        XContentBuilder json = jsonBuilder()
//                .startObject()
//                .field("date", new Date())
//                .endObject();
//        IndexResponse response = client.prepareIndex(indexName, typeName, "4")
//                .setSource(json)
//                .execute()
//                .actionGet();
//        String index = response.getIndex();
//        String type = response.getType();
//        String id = response.getId();
//        long version = response.getVersion();
//        boolean created = response.isCreated();
//        System.out.println(index + "," + type + "," + id + "," + version + "," + created);
//
//        json = jsonBuilder()
//                .startObject()
//                .field("date", new Date(), ISODateTimeFormat.dateTime().withZone(DateTimeZone.getDefault()))
//                .endObject();
//        response = client.prepareIndex(indexName, typeName, "5")
//                .setSource(json)
//                .execute()
//                .actionGet();
//    }
//
//    private static void searchWithBooleanFilter(String indexName, String typeName, String termName1, String termValue1,
//                                                String termName2, String termValue2, String sortField, String highlightField) {
//        //构建boolean query
//        FilterBuilder fb = boolFilter()
//                .must(termFilter(termName1, termValue1))
//                .must(termFilter(termName2, termValue2))
//                .cache(false);
//        FilteredQueryBuilder fqb = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(), fb);
//        //.mustNot(termQuery("content", "test2"))
//        //.should(termQuery("content", "test3"));
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        //设置search type
//                        //常用search type用：query_then_fetch
//                        //query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        //查询的termName和termvalue
//                .setQuery(fqb)
//                        //设置排序field
//                .addSort(sortField, SortOrder.DESC)
//                        //设置高亮field
//                .addHighlightedField(highlightField)
//                        //设置分页
//                .setFrom(0).setSize(60)
//                .execute()
//                .actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//    }
//
//    private static void searchWithFilter(String indexName, String typeName,
//                                         String... ids) {
//        FilteredQueryBuilder builder = QueryBuilders.filteredQuery(QueryBuilders.matchAllQuery(),
//                FilterBuilders.idsFilter(typeName).addIds(ids));
//        FilterBuilder filter = queryFilter(
//                QueryBuilders.queryStringQuery("成都").field("city").defaultOperator(QueryStringQueryBuilder.Operator.AND)
//        );
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                .setQuery(builder)
//                .setFrom(0).setSize(60)
//                .execute()
//                .actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//    }
//
//    private static void scrollSearch(String indexName, String typeName,
//                                     String... ids) {
//        IdsQueryBuilder qb = QueryBuilders.idsQuery().addIds(ids);
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                .setSearchType(SearchType.SCAN)
//                .setQuery(qb)
//                .setScroll(new TimeValue(100))
//                .setSize(50)
//                .execute()
//                .actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//
//        while (true) {
//            SearchHits hits = sResponse.getHits();
//            SearchHit[] hitArray = hits.getHits();
//            for (int i = 0; i < hitArray.length; i++) {
//                SearchHit hit = hitArray[i];
//                Map<String, Object> fields = hit.getSource();
//                for (String key : fields.keySet()) {
//                    System.out.println(key);
//                    System.out.println(fields.get(key));
//                }
//            }
//            sResponse = client.prepareSearchScroll(sResponse.getScrollId()).setScroll(new TimeValue(100)).execute().actionGet();
//            //Break condition: No hits are returned
//            if (sResponse.getHits().getHits().length == 0) {
//                break;
//            }
//        }
//    }
//
//    private static void deleteDocuments(String string, String string2) {
//        SearchResponse sResponse = client.prepareSearch(string)
//                .setTypes(string2)
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                .setQuery(QueryBuilders.matchAllQuery())
//                .setFrom(0).setSize(60)
//                .execute()
//                .actionGet();
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        List<String> ids = new ArrayList<String>(hitArray.length);
//        for (int i = 0; i < count; i++) {
//            System.out.println("==================================");
//            SearchHit hit = hitArray[i];
//            ids.add(hit.getId());
//
//        }
//        for (String id : ids) {
//            DeleteResponse response = client.prepareDelete(string, string2, id)
//                    .execute()
//                    .actionGet();
//        }
//
//    }
//
//    private static void dateRangeSearch(String indexName, String typeName,
//                                        String termName, String from, String to) {
//        // 构建range query
//        //2015-08-20 12:27:11
//        QueryBuilder qb = QueryBuilders.rangeQuery(termName).from(from).to(to);
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        // 设置search type
//                        // 常用search type用：query_then_fetch
//                        // query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        // 查询的termName和termvalue
//                .setQuery(qb)
//                        // 设置排序field
//                .addSort(termName, SortOrder.DESC)
//                        // 设置分页
//                .setFrom(0).setSize(60).execute().actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//
//    }
//
//    private static void dateRangeSearch2(String indexName, String typeName,
//                                         String termName, String from, String to) {
//        // 构建range query
//        //2015-08-20 12:27:11
//// DateTimeFormatter formatter = ISODateTimeFormat.basicDateTime();
//// LocalDate fromDateTime = formatter.parseLocalDate(from);
//// LocalDate toDateTime = formatter.parseLocalDate(to);
//        QueryBuilder qb = QueryBuilders.rangeQuery(termName).from(from).to(to);
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        // 设置search type
//                        // 常用search type用：query_then_fetch
//                        // query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        // 查询的termName和termvalue
//                .setQuery(qb)
//                        // 设置排序field
//                .addSort(termName, SortOrder.DESC)
//                        // 设置分页
//                .setFrom(0).setSize(60).execute().actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//
//    }
//
//    private static void countWithQuery(String indexName, String typeName, String termName, String termValue, String sortField, String highlightField) {
//        //search result get source
//        CountResponse cResponse = client.prepareCount(indexName)
//                .setTypes(typeName)
//                .setQuery(termQuery(termName, termValue))
//                .execute()
//                .actionGet();
//        int tShards = cResponse.getTotalShards();
//        int sShards = cResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + sShards);
//        long count = cResponse.getCount();
//
//    }
//
//    private static void rangeSearchWithOtherSearch(String indexName, String typeName,
//                                                   String termName, String min, String max, String termQueryField) {
//        // 构建range query
//        QueryBuilder qb = QueryBuilders.rangeQuery(termName).from(min).to(max);
//        TermQueryBuilder tb = termQuery(termName, termQueryField);
//        BoolQueryBuilder bq = boolQuery().must(qb).must(tb);
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        // 设置search type
//                        // 常用search type用：query_then_fetch
//                        // query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        // 查询的termName和termvalue
//                .setQuery(bq)
//                        // 设置排序field
//                .addSort(termName, SortOrder.DESC)
//                        // 设置分页
//                .setFrom(0).setSize(60).execute().actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//
//
//    }
//
//    private static void termRangeSearch(String indexName, String typeName,
//                                        String termName, String min, String max, String highlightField) {
//
//        // 构建range query
//        QueryBuilder qb = QueryBuilders.rangeQuery(termName).from(min).to(max);
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        // 设置search type
//                        // 常用search type用：query_then_fetch
//                        // query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        // 查询的termName和termvalue
//                .setQuery(qb)
//                        // 设置排序field
//                .addSort(termName, SortOrder.DESC)
//                        //设置高亮field
//                .addHighlightedField(highlightField)
//                        // 设置分页
//                .setFrom(0).setSize(60).execute().actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//
//
//    }
//
//    private static void sumOneField(String indexName, String typeName, String fieldName) {
//        SumBuilder sb = AggregationBuilders.sum("sum").field("fieldName");
//        //search result get source
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                .addAggregation(sb)
//                .execute()
//                .actionGet();
//        Map<String, Aggregation> aggMap = sResponse.getAggregations().asMap();
//        if (aggMap != null && aggMap.size() > 0) {
//            for (String key : aggMap.keySet()) {
//                Sum s = (Sum) aggMap.get(key);
//                System.out.println(s.getValue());
//            }
//        }
//    }
//
//    private static void searchWithTermQueryAndRetureSpecifiedFields(String indexName, String typeName, String termName,
//                                                                    String termValue, String sortField, String highlightField,
//                                                                    String... fields) {
//        // search result get specified fields
//        SearchRequestBuilder sb = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        // 设置search type
//                        // 常用search type用：query_then_fetch
//                        // query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        // 查询的termName和termvalue
//                .setQuery(termQuery(termName, termValue))
//                        // 设置排序field
//                .addSort(sortField, SortOrder.DESC)
//                        // 设置高亮field
//                .addHighlightedField(highlightField)
//                        // 设置分页
//                .setFrom(0).setSize(60);
//        for (String field : fields) {
//            sb.addField(field);
//        }
//        SearchResponse sResponse = sb.execute().actionGet();
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, SearchHitField> fm = hit.getFields();
//            for (String key : fm.keySet()) {
//                SearchHitField f = fm.get(key);
//                System.out.println(f.getName());
//                System.out.println(f.getValue());
//            }
//        }
//    }
//
//    private static void searchWithIds(String indexName, String typeName, String sortField, String highlightField,
//                                      String... ids) {
//        IdsQueryBuilder qb = QueryBuilders.idsQuery().addIds(ids);
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        //设置search type
//                        //常用search type用：query_then_fetch
//                        //query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        //查询的termName和termvalue
//                .setQuery(qb)
//                        //设置排序field
//                .addSort(sortField, SortOrder.DESC)
//                        //设置高亮field
//                .addHighlightedField(highlightField)
//                        //设置分页
//                .setFrom(0).setSize(60)
//                .execute()
//                .actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//
//    }
//
//    /**
//     * 在index:indexName, type:typeName中做通配符查询
//     *
//     * @param indexName
//     * @param typeName
//     * @param termName
//     * @param termValue
//     * @param sortField
//     * @param highlightField
//     */
//    private static void wildcardSearch(String indexName, String typeName, String termName, String termValue, String sortField, String highlightField) {
//        QueryBuilder qb = QueryBuilders.wildcardQuery(termName, termValue);
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        //设置search type
//                        //常用search type用：query_then_fetch
//                        //query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        //查询的termName和termvalue
//                .setQuery(qb)
//                        //设置排序field
//// .addSort(sortField, SortOrder.DESC)
//                        //设置高亮field
//// .addHighlightedField(highlightField)
//                        //设置分页
//                .setFrom(0).setSize(60)
//                .execute()
//                .actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//
//    }
//
//    /**
//     * 在index:indexName, type:typeName中做模糊查询
//     *
//     * @param indexName
//     * @param typeName
//     * @param termName
//     * @param termValue
//     * @param sortField
//     * @param highlightField
//     */
//    private static void fuzzySearch(String indexName, String typeName, String termName, String termValue, String sortField, String highlightField) {
//        QueryBuilder qb = QueryBuilders.fuzzyQuery(termName, termValue);
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        //设置search type
//                        //常用search type用：query_then_fetch
//                        //query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        //查询的termName和termvalue
//                .setQuery(qb)
//                        //设置排序field
//                .addSort(sortField, SortOrder.DESC)
//                        //设置高亮field
//                .addHighlightedField(highlightField)
//                        //设置分页
//                .setFrom(0).setSize(60)
//                .execute()
//                .actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//
//    }
//
//
//    /**
//     * 在index:indexName, type:typeName中做区间查询
//     *
//     * @param indexName
//     * @param typeName
//     * @param termName
//     * @param min
//     * @param max
//     * @param highlightField
//     */
//    private static void numericRangeSearch(String indexName, String typeName,
//                                           String termName, double min, double max, String highlightField) {
//        // 构建range query
//        QueryBuilder qb = QueryBuilders.rangeQuery(termName).from(min).to(max);
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        // 设置search type
//                        // 常用search type用：query_then_fetch
//                        // query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        // 查询的termName和termvalue
//                .setQuery(qb)
//                        // 设置排序field
//                .addSort(termName, SortOrder.DESC)
//                        //设置高亮field
//                .addHighlightedField(highlightField)
//                        // 设置分页
//                .setFrom(0).setSize(60).execute().actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//
//    }
//
//
//    /**
//     * 在索引indexName, type为typeName中查找两个term：term1(termName1, termValue1)和term2(termName2, termValue2)
//     *
//     * @param indexName
//     * @param typeName
//     * @param termName1
//     * @param termValue1
//     * @param termName2
//     * @param termValue2
//     * @param sortField
//     * @param highlightField
//     */
//    private static void searchWithBooleanQuery(String indexName, String typeName, String termName1, String termValue1,
//                                               String termName2, String termValue2, String sortField, String highlightField) {
//        //构建boolean query
//        BoolQueryBuilder bq = boolQuery()
//                .must(termQuery(termName1, termValue1))
//                .must(termQuery(termName2, termValue2));
//        //.mustNot(termQuery("content", "test2"))
//        //.should(termQuery("content", "test3"));
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        //设置search type
//                        //常用search type用：query_then_fetch
//                        //query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        //查询的termName和termvalue
//                .setQuery(bq)
//                        //设置排序field
//                .addSort(sortField, SortOrder.DESC)
//                        //设置高亮field
//                .addHighlightedField(highlightField)
//                        //设置分页
//                .setFrom(0).setSize(60)
//                .execute()
//                .actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//        System.out.println(tShards + "," + timeCost + "," + sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//    }
//
//
//    /**
//     * 在索引indexName, type为typeName中查找term(termName, termValue)
//     *
//     * @param indexName
//     * @param typeName
//     * @param termName
//     * @param termValue
//     * @param sortField
//     * @param highlightField
//     */
//    private static void searchWithTermQuery(String indexName, String typeName, String termName, String termValue, String sortField, String highlightField) {
//        //search result get source
//
//        SearchResponse sResponse = client.prepareSearch(indexName)
//                .setTypes(typeName)
//                        //设置search type
//                        //常用search type用：query_then_fetch
//                        //query_then_fetch是先查到相关结构，然后聚合不同node上的结果后排序
//                .setSearchType(SearchType.QUERY_THEN_FETCH)
//                        //查询的termName和termvalue
//                .setQuery(termQuery(termName, termValue))
//                        //设置排序field
//// .addSort(sortField, SortOrder.DESC)
//                        //设置高亮field
//// .addHighlightedField(highlightField)
//                        //设置分页
//                .setFrom(0).setSize(60)
//                .execute()
//                .actionGet();
//        int tShards = sResponse.getTotalShards();
//        long timeCost = sResponse.getTookInMillis();
//        int sShards = sResponse.getSuccessfulShards();
//// System.out.println(tShards+","+timeCost+","+sShards);
//        SearchHits hits = sResponse.getHits();
//        long count = hits.getTotalHits();
//        SearchHit[] hitArray = hits.getHits();
//        for (int i = 0; i < count; i++) {
//            System.out.println("==================================");
//            SearchHit hit = hitArray[i];
//            Map<String, Object> fields = hit.getSource();
//            for (String key : fields.keySet()) {
//                System.out.println(key);
//                System.out.println(fields.get(key));
//            }
//        }
//    }
//
//    /**
//     * 用docId获取document
//     *
//     * @param indexName
//     * @param typeName
//     * @param docId
//     */
//    private static void getWithId(String indexName, String typeName, String docId) {
//        //get with id
//        GetResponse gResponse = client.prepareGet(indexName, typeName, docId)
//                .execute()
//                .actionGet();
//        System.out.println(gResponse.getIndex());
//        System.out.println(gResponse.getType());
//        System.out.println(gResponse.getVersion());
//        System.out.println(gResponse.isExists());
//        Map<String, Object> results = gResponse.getSource();
//        if (results != null) {
//            for (String key : results.keySet()) {
//                Object field = results.get(key);
//                System.out.println(key);
//                System.out.println(field);
//            }
//        }
//    }
//
//    /**
//     * 用javabean构建document
//     *
//     * @throws JsonProcessingException
//     */
//    private static void indexWithBean(String indexName, String typeName) throws IOException {
//        class InternalBean {
//            private String user;
//            private Date postDate;
//            private float price;
//            private String message;
//            private String tid;
//            private String location;
//            private String endTime;
//
//            public String getEndTime() {
//                return endTime;
//            }
//
//            public void setEndTime(String endTime) {
//                this.endTime = endTime;
//            }
//
//            public String getLocation() {
//                return location;
//            }
//
//            public void setLocation(String location) {
//                this.location = location;
//            }
//
//            public String getTid() {
//                return tid;
//            }
//
//            public void setTid(String tid) {
//                this.tid = tid;
//            }
//
//            @SuppressWarnings("unused")
//            public String getUser() {
//                return user;
//            }
//
//            public void setUser(String user) {
//                this.user = user;
//            }
//
//            @SuppressWarnings("unused")
//            public Date getPostDate() {
//                return postDate;
//            }
//
//            public void setPostDate(Date postDate) {
//                this.postDate = postDate;
//            }
//
//            @SuppressWarnings("unused")
//            public String getMessage() {
//                return message;
//            }
//
//            public void setMessage(String message) {
//                this.message = message;
//            }
//
//            @SuppressWarnings("unused")
//            public float getPrice() {
//                return price;
//            }
//
//            public void setPrice(float price) {
//                this.price = price;
//            }
//
//        }
//        //设置javabean的属性
//        InternalBean bean = new InternalBean();
//        bean.setUser("kimchy3");
//        bean.setPrice(6.5f);
//        bean.setMessage("trying out Elasticsearch");
//        bean.setPostDate(new Date());
//        bean.setTid("10003");
//        bean.setLocation("-77.03653, 38.897676");
//        bean.setEndTime("2015-08-29 15:00:00");
//        //用javabean构建json对象
//        ObjectMapper mapper = new ObjectMapper();
//        byte[] json = mapper.writeValueAsBytes(bean);
//        //指定索引名称，type名称和documentId(documentId可选，不设置则系统自动生成)创建document
//        IndexResponse response = client.prepareIndex(indexName, typeName, "3")
//                .setSource(json)
//                .execute()
//                .actionGet();
//        String index = response.getIndex();
//        String type = response.getType();
//        String id = response.getId();
//        long version = response.getVersion();
//        boolean created = response.isCreated();
//        System.out.println(index + "," + type + "," + id + "," + version + "," + created);
//    }
//
//    /**
//     * 用java的map构建document
//     */
//    private static void indexWithMap(String indexName, String typeName) {
//        Map<String, Object> json = new HashMap<String, Object>();
//        //设置document的field
//        json.put("user", "kimchy2");
//        json.put("postDate", new Date());
//        json.put("price", 6.4);
//        json.put("message", "trying out Elasticsearch");
//        json.put("tid", "10002");
//        json.put("location", "-77.03653, 38.897676");
//        json.put("endTime", "2015-08-25 09:00:00");
//        //指定索引名称，type名称和documentId(documentId可选，不设置则系统自动生成)创建document
//        IndexResponse response = client.prepareIndex(indexName, typeName, "2")
//                .setSource(json)
//                .execute()
//                .actionGet();
//        //response中返回索引名称，type名称，doc的Id和版本信息
//        String index = response.getIndex();
//        String type = response.getType();
//        String id = response.getId();
//        long version = response.getVersion();
//        boolean created = response.isCreated();
//        System.out.println(index + "," + type + "," + id + "," + version + "," + created);
//    }
//
//    /**
//     * 用java字符串创建document
//     */
//    private static void indexWithStr(String indexName, String typeName) {
//        //手工构建json字符串
//        //该document包含user, postData和message三个field
//        String json = "{" +
//                "\"user\":\"kimchy\"," +
//                "\"postDate\":\"2013-01-30\"," +
//                "\"price\":\"6.3\"," +
//                "\"tid\":\"10001\"," +
//                "\"location\":\"-77.03653, 38.897676\"," +
//                "\"endTime\":\"2015-08-19 09:00:00\"," +
//                "\"message\":\"trying out Elasticsearch\"" +
//                "}";
//        //指定索引名称，type名称和documentId(documentId可选，不设置则系统自动生成)创建document
//        IndexResponse response = client.prepareIndex(indexName, typeName, "1")
//                .setSource(json)
//                .execute()
//                .actionGet();
//        //response中返回索引名称，type名称，doc的Id和版本信息
//        String index = response.getIndex();
//        String type = response.getType();
//        String id = response.getId();
//        long version = response.getVersion();
//        boolean created = response.isCreated();
//        System.out.println(index + "," + type + "," + id + "," + version + "," + created);
//    }
//
//    /**
//     * 创建es client
//     * clusterName:集群名字
//     * nodeIp:集群中节点的ip地址
//     * nodePort:节点的端口
//     *
//     * @return
//     */
//    private static Client getClient(String clusterName, String nodeIp, int nodePort) {
//        //设置集群的名字
//        Settings settings = ImmutableSettings.settingsBuilder()
//                .put("cluster.name", clusterName)
//// .put("number_of_shards", 1)
//// .put("number_of_replicas", 0)
//                .build();
//
//        //创建集群client并添加集群节点地址
//        Client client = new TransportClient(settings)
//// .addTransportAddress(new InetSocketTransportAddress("192.168.200.195", 9370))
//// .addTransportAddress(new InetSocketTransportAddress("192.168.200.196", 9370))
//// .addTransportAddress(new InetSocketTransportAddress("192.168.200.197", 9370))
//// .addTransportAddress(new InetSocketTransportAddress("192.168.200.198", 9370))
//                .addTransportAddress(
//                        new InetSocketTransportAddress(nodeIp,
//                                nodePort));
//
//        return client;
//    }
//
//
//    private static void deleteDocWithId(String indexName, String typeName, String docId) {
//        DeleteResponse dResponse = client.prepareDelete(indexName, typeName, docId)
//                .execute()
//                .actionGet();
//        String index = dResponse.getIndex();
//        String type = dResponse.getType();
//        String id = dResponse.getId();
//        long version = dResponse.getVersion();
//        System.out.println(index + "," + type + "," + id + "," + version);
//    }
//
//    /**
//     * 创建索引
//     * 注意：在生产环节中通知es集群的owner去创建index
//     *
//     * @param indexName
//     * @param documentType
//     * @throws IOException
//     */
//    private static void createIndex(String indexName, String documentType) throws IOException {
//        final IndicesExistsResponse iRes = client.admin().indices().prepareExists(indexName).execute().actionGet();
//        if (iRes.isExists()) {
//            client.admin().indices().prepareDelete(indexName).execute().actionGet();
//        }
//        client.admin().indices().prepareCreate(indexName).setSettings(ImmutableSettings.settingsBuilder().put("number_of_shards", 1).put("number_of_replicas", "0")).execute().actionGet();
//        XContentBuilder mapping = jsonBuilder()
//                .startObject()
//                .startObject(documentType)
//// .startObject("_routing").field("path","tid").field("required", "true").endObject()
//                .startObject("_source").field("enabled", "true").endObject()
//                .startObject("_all").field("enabled", "false").endObject()
//                .startObject("properties")
//                .startObject("user")
//                .field("store", true)
//                .field("type", "string")
//                .field("index", "not_analyzed")
//                .endObject()
//                .startObject("message")
//                .field("store", true)
//                .field("type", "string")
//                .field("index", "analyzed")
//                .field("index_analyzer", "standard")
//                .endObject()
//                .startObject("price")
//                .field("store", true)
//                .field("type", "float")
//                .endObject()
//                .startObject("tid")
//                .field("store", true)
//                .field("type", "string")
//                .field("index", "not_analyzed")
//                .endObject()
//                .startObject("location")
//                .field("store", true)
//                .field("type", "geo_point")
//                .field("lat_lon", true)
//                .field("geohash", true)
//                .field("geohash_prefix", true)
//                .field("geohash_precision", 7)
//                .endObject()
//                .startObject("shape")
//                .field("store", true)
//                .field("type", "geo_shape")
//                .field("geohash", true)
//                .field("geohash_prefix", false)
//                .field("geohash_precision", 7)
//                .endObject()
//                .startObject("endTime")
//                .field("type", "date")
//                .field("store", true)
//                .field("index", "not_analyzed")
//                        //2015-08-21T08:35:13.890Z
//                .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//                .endObject()
//                .startObject("date")
//                .field("type", "date")
//// .field("store", true)
//// .field("index", "not_analyzed")
//                        //2015-08-21T08:35:13.890Z
//// .field("format", "yyyy-MM-dd HH:mm:ss||yyyy-MM-dd'T'HH:mm:ss.SSSZ")
//                .endObject()
//                .endObject()
//                .endObject()
//                .endObject();
//        client.admin().indices()
//                .preparePutMapping(indexName)
//                .setType(documentType)
//                .setSource(mapping)
//                .execute().actionGet();
//    }
//}
