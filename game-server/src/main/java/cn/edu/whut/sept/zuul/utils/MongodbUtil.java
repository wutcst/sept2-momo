/**
 * @description : [Mongodb的通用工具类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-19 23:17]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 23:00]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.utils;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientOptions.Builder;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * MongoDB工具类 Mongo实例代表了一个数据库连接池，即使在多线程的环境中，一个Mongo实例对我们来说已经足够了<br>
 * 注意Mongo已经实现了连接池，并且是线程安全的。 <br>
 * 设计为单例模式， 因 MongoDB的Java驱动是线程安全的，对于一般的应用，只要一个Mongo实例即可，<br>
 * Mongo有个内置的连接池（默认为10个） 对于有大量写和读的环境中，为了确保在一个Session中使用同一个DB时，<br>
 * DB和DBCollection是绝对线程安全的<br>
 *
 * @author zhoulingfei
 * @version 0.0.0
 * @date 2015-5-29 上午11:49:49
 * @Copyright (c)1997-2015 NavInfo Co.Ltd. All Rights Reserved.
 */
public enum MongodbUtil {

    /**
     * 定义一个枚举的元素，它代表此类的一个实例
     */
    instance;

    private MongoClient mongoClient;

    static {
        System.out.println("===============MongoDBUtil init========================");
        CompositeConfiguration config = new CompositeConfiguration();
        try {
            config.addConfiguration(new PropertiesConfiguration("mongodb.properties"));
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
        // 从配置文件中获取属性值
        String ip = config.getString("host");
        int port = config.getInt("port");
        instance.mongoClient = new MongoClient(ip, port);

        // or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
        // List<ServerAddress> listHost = Arrays.asList(new ServerAddress("localhost", 27017),new ServerAddress("localhost", 27018));
        // instance.mongoClient = new MongoClient(listHost);

        // 大部分用户使用mongodb都在安全内网下，但如果将mongodb设为安全验证模式，就需要在客户端提供用户名和密码：
        // boolean auth = db.authenticate(myUserName, myPassword);
        Builder options = new MongoClientOptions.Builder();
        // options.autoConnectRetry(true);// 自动重连true
        // options.maxAutoConnectRetryTime(10); // the maximum auto connect retry time
        options.connectionsPerHost(300);// 连接池设置为300个连接,默认为100
        options.connectTimeout(15000);// 连接超时，推荐>3000毫秒
        options.maxWaitTime(5000); //
        options.socketTimeout(0);// 套接字超时时间，0无限制
        options.threadsAllowedToBlockForConnectionMultiplier(5000);// 线程队列数，如果连接线程排满了队列就会抛出“Out of semaphores to get db”错误。
        options.writeConcern(WriteConcern.SAFE);//
        options.build();
    }

    // ------------------------------------共用方法---------------------------------------------------

    /**
     * 获取DB实例 - 指定DB
     *
     * @param dbName
     * @return
     */
    public MongoDatabase getDB(String dbName) {
        if (dbName == null || "".equals(dbName)) {
            return null;
        }
        MongoDatabase database = mongoClient.getDatabase(dbName);
        return database;
    }

    /**
     * 获取collection对象 - 指定Collection
     *
     * @param collName
     * @return
     */
    public MongoCollection<Document> getCollection(String dbName, String collName) {
        if (collName != null && !"".equals(collName)) {
            if (dbName != null && !"".equals(dbName)) {
                MongoCollection<Document> collection = mongoClient.getDatabase(dbName).getCollection(collName);
                return collection;
            }
        }
        return null;
    }

    /**
     * 查询DB下的所有表名
     */
    public List<String> getAllCollections(String dbName) {
        MongoDatabase database = getDB(dbName);
        if (database == null) {
            return null;
        }
        MongoIterable<String> colls = database.listCollectionNames();
        List<String> _list = new ArrayList<String>();
        for (String s : colls) {
            _list.add(s);
        }
        return _list;
    }

    /**
     * 获取所有数据库名称列表
     *
     * @return
     */
    public MongoIterable<String> getAllDBNames() {
        MongoIterable<String> dbNames = mongoClient.listDatabaseNames();
        return dbNames;
    }
    /**
     * 删除一个数据库
     */
    public void dropDB(String dbName) {
        getDB(dbName).drop();
    }

    /**
     * 查找对象 - 根据主键_id
     *
     * @param coll
     * @param id
     * @return
     */
    public Document findById(MongoCollection<Document> coll, String id) {
        ObjectId _idobj;
        try {
            _idobj = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
        return coll.find(Filters.eq("_id", _idobj)).first();
    }

    /**
     * 统计数
     */
    public int getCount(MongoCollection<Document> coll) {
        long count = coll.countDocuments();
        return (int) count;
    }

    /**
     * 插入文档
     */
    public void insertOne(String dbName, String collName, Document doc) {
        MongoCollection<Document> collection = getCollection(dbName, collName);
        if (collection != null) {
            collection.insertOne(doc);
        }
    }

    /**
     * 条件查询
     */
    public MongoCursor<Document> find(MongoCollection<Document> coll, Bson filter) {
        return coll.find(filter).iterator();
    }

    /**
     * 分页查询
     */
    public MongoCursor<Document> findByPage(MongoCollection<Document> coll, Bson filter, int pageNo, int pageSize) {
        Bson orderBy = new BasicDBObject("_id", 1);
        return coll.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
    }

    /**
     * 通过ID删除
     *
     * @param coll
     * @param id
     * @return
     */
    public int deleteById(MongoCollection<Document> coll, String id) {
        int count = 0;
        ObjectId _id;
        try {
            _id = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return 0;
        }
        Bson filter = Filters.eq("_id", _id);
        DeleteResult deleteResult = coll.deleteOne(filter);
        count = (int) deleteResult.getDeletedCount();
        return count;
    }

    /**
     * @param coll
     * @param id
     * @param newdoc
     * @return
     */
    public Document updateById(MongoCollection<Document> coll, String id, Document newdoc) {
        ObjectId _idobj;
        try {
            _idobj = new ObjectId(id);
        } catch (IllegalArgumentException e) {
            return null;
        }
        Bson filter = Filters.eq("_id", _idobj);
        coll.updateOne(filter, new Document("$set", newdoc));
        return newdoc;
    }

    public void dropCollection(String dbName, String collName) {
        getDB(dbName).getCollection(collName).drop();
    }

    /**
     * 关闭Mongodb
     */
    public void close() {
        if (mongoClient != null) {
            mongoClient.close();
            mongoClient = null;
        }
    }
}