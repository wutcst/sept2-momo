/**
 * @description : [用户操作路由处理]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-18 21:14]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 23:00]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.action;

import cn.edu.whut.sept.zuul.entity.Player;
import cn.edu.whut.sept.zuul.protoBuf.RoomProtoBuf;
import cn.edu.whut.sept.zuul.utils.MongodbUtil;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.bolt.broker.client.kit.UserIdSettingKit;
import com.iohao.game.common.kit.RandomKit;
import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

import java.util.List;

import static cn.edu.whut.sept.zuul.dao.UserDao.addPlayer;

@Slf4j
@ActionController(2)
public class UserAction {

    @ActionMethod(0)
    public Player login(Player player, FlowContext flowContext) {
        String name = player.getName();
        String pwd = player.getPwd();

        log.info("数据库······");

        MongoCollection<Document> players = MongodbUtil.instance.getCollection("woz", "player");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("name", name);
        FindIterable<Document> cursor = players.find(whereQuery);
        Document document = cursor.first();
        if (document == null) {
            document = addPlayer(name, pwd);
        }
        List<String> items = document.getList("items", String.class);
        List<String> place = document.getList("place", String.class);
        int packWeight = document.getInteger("packWeight");
        player.setPlace(place);
        player.setPackWeight(packWeight);
        player.setItems(items);

        long userId = RandomKit.randomInt(1400000, 1500000);
        boolean success = UserIdSettingKit.settingUserId(flowContext, userId);
        if (!success) {
            log.error("设置用户 userId 失败");
        }
        return player;
    }

    @ActionMethod(5)
    public RoomProtoBuf getRoomItem(RoomProtoBuf roomProtoBuf, FlowContext flowContext) {
        String roomName = roomProtoBuf.getName();
        MongoCollection<Document> rooms = MongodbUtil.instance.getCollection("woz", "room");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("name", roomName);
        Document roomDoc = rooms.find(whereQuery).first();
        assert roomDoc != null;
        roomProtoBuf.setItems(roomDoc.getList("items", String.class));
        return roomProtoBuf;
    }

    @ActionMethod(9)
    public Player updatePlayer(Player player, FlowContext flowContext) {
        MongoCollection<Document> players = MongodbUtil.instance.getCollection("woz", "player");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("name", player.getName());
        Document newPlayerDocument = new Document();
        newPlayerDocument.put("name", player.getName());
        newPlayerDocument.put("pwd", player.getPwd());
        newPlayerDocument.put("place", player.getPlace());
        newPlayerDocument.put("items", player.getItems());
        newPlayerDocument.put("packWeight", player.getPackWeight());
        players.findOneAndReplace(whereQuery, newPlayerDocument);
        return player;
    }
}

