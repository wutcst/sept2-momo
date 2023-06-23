/**
 * @description : [用户的数据库操作]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-23 19:00]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-23 19:00]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.dao;

import cn.edu.whut.sept.zuul.utils.MongodbUtil;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

public class UserDao {
    public static Document addPlayer(String name, String pwd) {
        Document newPlayer = new Document();
        newPlayer.put("name", name);
        newPlayer.put("pwd", pwd);
        newPlayer.put("packWeight", 5);
        List<String> place = new ArrayList<>();
        place.add("outside");
        newPlayer.put("place", place);
        List<String> items = new ArrayList<>();
        newPlayer.put("items", items);
        MongodbUtil.instance.insertOne("woz", "player", newPlayer);
        return newPlayer;
    }
}
