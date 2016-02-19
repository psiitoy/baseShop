package com.rise.shop.persistence.dao.mongo.utils;

import com.mongodb.*;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangdi on 15-1-9.
 */
public class MongoDBManager {
    private static Logger log = Logger.getLogger(MongoDBManager.class);

    private static Mongo mg = null;
    private static DB db = null;

    private String address;
    private String username;
    private String password;
    private String database;
    private int connectionsPerHost;
    private int threadsAllowedToBlockForConnectionMultiplier;
    // 是否需要鉴权,先不做修改，参数放这里
    private boolean authAble = false;

    public void init() {
        try {
            if (StringUtils.isEmpty(address))
                throw new UnknownHostException();
            String[] hosts = StringUtils.split(address, ",");
            if (ArrayUtils.isEmpty(hosts)) {
                throw new UnknownHostException();
            } else {
                MongoOptions options = new MongoOptions();
                options.connectionsPerHost = connectionsPerHost;
                options.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
                options.autoConnectRetry = true;
                if (hosts.length == 1) {
                    mg = new Mongo(hosts[0], options);
                } else {
                    List<ServerAddress> list = new ArrayList<ServerAddress>();
                    for (String h : hosts) {
                        String[] hp = h.split(":");
                        list.add(new ServerAddress(hp[0], Integer.parseInt(hp[1])));
                    }
                    mg = new Mongo(list, options);
                }
            }
            db = mg.getDB(database);
            if (StringUtils.isNotEmpty(username)) {
                boolean sign = db.authenticate(username, password.toCharArray());
                if (!sign) {
                    throw new MongoException("username:" + username + " 不正确");
                }
            }
            //创建索引
            createIndex();
        } catch (UnknownHostException e) {
            log.error(address + "错误", e);
        } catch (MongoException e) {
            log.error("MongoDB创建失败", e);
        }
    }

    /**
     * 关闭MONGO
     */
    public void destory() {
        if (mg != null)
            mg.close();
        mg = null;
        db = null;
    }

    static {
        try {

        } catch (Exception e) {
            log.error("Can't connect MongoDB!");
            e.printStackTrace();
        }
    }

    /**
     * 获取集合（表）
     *
     * @param collection
     */
    public static DBCollection getCollection(String collection) {
        return db.getCollection(collection);
    }

    /**
     * ----------------------------------分割线--------------------------------------
     */

    /**
     * 插入
     *
     * @param collection
     * @param map
     */
    public void insert(String collection, Map<String, Object> map) {
        try {
            DBObject dbObject = map2Obj(map);
            getCollection(collection).insert(dbObject);
        } catch (MongoException e) {
            log.error("MongoException:" + e.getMessage());
        }
    }

    /**
     * 批量插入
     *
     * @param collection
     * @param list
     */
    public void insertBatch(String collection, List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        try {
            List<DBObject> listDB = new ArrayList<DBObject>();
            for (int i = 0; i < list.size(); i++) {
                DBObject dbObject = map2Obj(list.get(i));
                listDB.add(dbObject);
            }
            getCollection(collection).insert(listDB);
        } catch (MongoException e) {
            log.error("MongoException:" + e.getMessage());
        }
    }

    /**
     * 删除
     *
     * @param collection
     * @param map
     */
    public void delete(String collection, Map<String, Object> map) {
        DBObject obj = map2Obj(map);
        getCollection(collection).remove(obj);
    }

    /**
     * 删除全部
     *
     * @param collection
     */
    public void deleteAll(String collection) {
        List<DBObject> rs = findAll(collection);
        if (rs != null && !rs.isEmpty()) {
            for (int i = 0; i < rs.size(); i++) {
                getCollection(collection).remove(rs.get(i));
            }
        }
    }

    /**
     * 批量删除
     *
     * @param collection
     * @param list
     */
    public void deleteBatch(String collection, List<Map<String, Object>> list) {
        if (list == null || list.isEmpty()) {
            return;
        }
        for (int i = 0; i < list.size(); i++) {
            getCollection(collection).remove(map2Obj(list.get(i)));
        }
    }

    /**
     * 计算满足条件条数
     *
     * @param collection
     * @param map
     */
    public long getCount(String collection, Map<String, Object> map) {
        return getCollection(collection).getCount(map2Obj(map));
    }

    /**
     * 计算集合总条数
     *
     * @param collection
     */
    public long getCount(String collection) {
        return getCollection(collection).find().count();
    }

    /**
     * 更新
     *
     * @param collection
     * @param setFields
     * @param whereFields
     */
    public void update(String collection, Map<String, Object> setFields,
                       Map<String, Object> whereFields) {
        DBObject obj1 = map2Obj(setFields);
        DBObject obj2 = map2Obj(whereFields);
        getCollection(collection).updateMulti(obj1, obj2);
    }

    public int update(String collection, final Map<String, Object> setFields) {
//        DBObject q = findByObjId(collection, setFields.get("_id").toString());
//        DBObject o = map2Obj(setFields);
//        getCollection(collection).update(q, o);
        try {
            List<DBObject> q = find(collection, new HashMap<String, Object>() {{
                put("id", setFields.get("id"));
            }});
            if (!q.isEmpty()) {
                DBObject o = map2Obj(setFields);
                WriteResult wr = getCollection(collection).update(q.get(0), MongoUtils.refreshMap(q.get(0), setFields));
                return wr.getN();
            } else {
                return 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }

    }

    /**
     * 查找对象（根据主键_id）
     *
     * @param collection
     * @param _id
     */
    public DBObject findByObjId(String collection, String _id) {
        DBObject obj = new BasicDBObject();
        obj.put("_id", ObjectId.massageToObjectId(_id));
        return getCollection(collection).findOne(obj);
    }

    /**
     * 查找集合所有对象
     *
     * @param collection
     */
    public List<DBObject> findAll(String collection) {
        return getCollection(collection).find().toArray();
    }

    /**
     * 查找（返回一个对象）
     *
     * @param map
     * @param collection
     */
    public DBObject findOne(String collection, Map<String, Object> map) {
        DBCollection coll = getCollection(collection);
        return coll.findOne(map2Obj(map));
    }

    /**
     * 查找（返回一个List<DBObject>）
     *
     * @param map
     * @param collection
     * @throws Exception
     */
    public List<DBObject> find(String collection, Map<String, Object> map) throws Exception {
        DBCollection coll = getCollection(collection);
        DBCursor c = coll.find(map2Obj(map));
        if (c != null)
            return c.toArray();
        else
            return null;
    }

    public List<DBObject> findByPage(String collection, Map<String, Object> map, int index, int pageSize) throws Exception {
        DBCollection coll = getCollection(collection);
        DBCursor c = coll.find(map2Obj(map)).skip(index).limit(pageSize);
        if (c != null)
            return c.toArray();
        else
            return null;
    }

    public DBObject map2Obj(Map<String, Object> map) {
        DBObject obj = new BasicDBObject();
        if (map.containsKey("class") && map.get("class") instanceof Class)
            map.remove("class");
        obj.putAll(map);
        return obj;
    }


    //    索引文件
    private Map<String, List<String>> mongoCollectionsIndexesInfoMap;
    public static final String FIELD_UNIQUE = "unique";
    public static final String FIELD_BACKGROUD = "background";
    public static final String _ID = "_id";

    /**
     * 启动时创建索引
     *
     * @return
     */
    public void createIndex() {
        for (Map.Entry<String, List<String>> entry : mongoCollectionsIndexesInfoMap.entrySet()) {
            String collectionName = entry.getKey();
            List<String> indexesInfo = entry.getValue();
            DBCollection collection = getCollection(collectionName);
            if (collection != null && indexesInfo != null && !indexesInfo.isEmpty()) {
                DBObject keys = null;
                boolean unique = false;
                boolean backgroud = false;
                for (String json : indexesInfo) {
                    try {
                        keys = fitIndexDBObject(json);
                        if (keys == null)
                            continue;
                        if (keys.containsField(_ID)) {
                            //tokumx 不允许重复建立 _id 对应索引
                            continue;
                        }
                        if (keys.get(FIELD_UNIQUE) != null)
                            unique = Boolean.parseBoolean(keys.get(FIELD_UNIQUE).toString());
                        if (keys.get(FIELD_BACKGROUD) != null)
                            backgroud = Boolean.parseBoolean(keys.get(FIELD_BACKGROUD).toString());
                        keys.removeField(FIELD_UNIQUE);
                        keys.removeField(FIELD_BACKGROUD);
                        DBObject options = new BasicDBObject();
                        if (unique)
                            options.put("unique", Boolean.TRUE);
                        if (!backgroud)
                            options.put("background", Boolean.FALSE);
                        collection.ensureIndex(keys, options);
                    } catch (Exception e) {
                        throw new RuntimeException("Mongodb index create error by: " + json, e);
                    }
                }
            }
        }
    }

    private static final org.codehaus.jackson.map.ObjectMapper om = new org.codehaus.jackson.map.ObjectMapper();

    private DBObject fitIndexDBObject(String json) {
        try {
            Map map = om.readValue(json, Map.class);
            if (map == null)
                return null;
            return new BasicDBObject(map);
        } catch (Exception e) {
            log.error("Mongodb index Format error ", e);
            return null;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDatabase() {
        return database;
    }

    public void setDatabase(String database) {
        this.database = database;
    }

    public int getConnectionsPerHost() {
        return connectionsPerHost;
    }

    public void setConnectionsPerHost(int connectionsPerHost) {
        this.connectionsPerHost = connectionsPerHost;
    }

    public int getThreadsAllowedToBlockForConnectionMultiplier() {
        return threadsAllowedToBlockForConnectionMultiplier;
    }

    public void setThreadsAllowedToBlockForConnectionMultiplier(int threadsAllowedToBlockForConnectionMultiplier) {
        this.threadsAllowedToBlockForConnectionMultiplier = threadsAllowedToBlockForConnectionMultiplier;
    }

    public boolean isAuthAble() {
        return authAble;
    }

    public void setAuthAble(boolean authAble) {
        this.authAble = authAble;
    }

    public Map<String, List<String>> getMongoCollectionsIndexesInfoMap() {
        return mongoCollectionsIndexesInfoMap;
    }

    public void setMongoCollectionsIndexesInfoMap(Map<String, List<String>> mongoCollectionsIndexesInfoMap) {
        this.mongoCollectionsIndexesInfoMap = mongoCollectionsIndexesInfoMap;
    }
}
