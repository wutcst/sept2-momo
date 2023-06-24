/**
 * @description : [连接路由处理]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-18 22:20]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 23:00]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.action;

import cn.edu.whut.sept.zuul.protoBuf.ConnectVerify;
import cn.edu.whut.sept.zuul.protoBuf.RoomProtoBuf;
import cn.edu.whut.sept.zuul.utils.MongodbUtil;
import com.iohao.game.action.skeleton.annotation.ActionController;
import com.iohao.game.action.skeleton.annotation.ActionMethod;
import com.iohao.game.action.skeleton.core.flow.FlowContext;
import com.iohao.game.common.kit.NetworkKit;
import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;

@ActionController(1)
@Slf4j
public class ConnectAction {
    @ActionMethod(0)
    public ConnectVerify connectVerify(ConnectVerify connectVerify) {
        ConnectVerify newConnectVerify = new ConnectVerify();
        newConnectVerify.setMsg("hello, serverIP: " + NetworkKit.LOCAL_IP);
        return newConnectVerify;
    }

    @ActionMethod(2)
    public RoomProtoBuf updateAllRoom(RoomProtoBuf roomProtoBuf, FlowContext flowContext) {
        MongoCollection<Document> rooms = MongodbUtil.instance.getCollection("woz", "room");
        BasicDBObject whereQuery = new BasicDBObject();
        whereQuery.put("name", roomProtoBuf.getName());
        rooms.findOneAndUpdate(whereQuery, new Document("$set", new Document("items", roomProtoBuf.getItems())));
        return roomProtoBuf;
    }
}
