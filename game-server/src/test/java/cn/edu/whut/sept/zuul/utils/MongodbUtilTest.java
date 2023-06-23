/**
 * @description : [MongodbUtil类的测试类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-18 19:59]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-18 19:59]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.utils;

import com.mongodb.client.MongoDatabase;
import org.junit.Test;

public class MongodbUtilTest {
    @Test
    public void getMongoConnectionByAddressTest(){
        MongoDatabase local = MongodbUtil.instance.getDB("local");
        System.out.println("local = " + local);
    }

}
