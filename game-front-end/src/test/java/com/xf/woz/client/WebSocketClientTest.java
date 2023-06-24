/**
 * @description : [一句话描述该类的功能]
 * @author : [姜明昆]
 * @version : [v1.0]
 * @createTime : [2022-12-18 23:31]
 * @updateUser : [姜明昆]
 * @updateTime : [2022-12-18 23:31]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.client;

import cn.hutool.json.JSONUtil;
import com.iohao.game.bolt.broker.client.external.bootstrap.ExternalKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.xf.woz.protoBuf.ConnectVerify;
import org.junit.Test;

public class WebSocketClientTest {
    @Test
    public void decodeTest(){
        int cmd = 0;
        int subCmd = 0;
        ConnectVerify connectReq = new ConnectVerify();

        connectReq.setMsg("jack");
        String s = JSONUtil.toJsonStr(connectReq);
        ExternalMessage externalMessage = ExternalKit
                // 路由、子路由、业务数据
                .createExternalMessage(cmd, subCmd, connectReq);
        System.out.println(externalMessage);
//        System.out.println("CommentWebSocketClient.onOpen");
        // 游戏框架内置的协议， 与游戏前端相互通信的协议
    }
}
