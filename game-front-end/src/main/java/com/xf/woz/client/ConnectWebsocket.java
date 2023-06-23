/**
 * @description : [服务器连接测试的websocket]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-18 22:13]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-18 22:13]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.client;


import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.ExternalKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.common.kit.NetworkKit;
import com.xf.woz.protoBuf.ConnectVerify;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;

@Slf4j
public class ConnectWebsocket extends WebSocketClient {

    public ConnectWebsocket(URI serverUri, Draft protocolDraft) {
        super(serverUri, protocolDraft);
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        // 路由, 对应服务端逻辑服的业务类路由地址
        int cmd = 1;
        int subCmd = 0;
        ConnectVerify connectReq = new ConnectVerify();
        connectReq.setMsg(NetworkKit.LOCAL_IP);

        ExternalMessage externalMessage = ExternalKit
                // 路由、子路由、业务数据
                .createExternalMessage(cmd, subCmd, connectReq);

        // 转为 pb 字节
        byte[] bytes = DataCodecKit.encode(externalMessage);
        // 发送数据到游戏服务器
        this.send(bytes);
    }

    @Override
    public void onMessage(String s) {
        System.out.println("CommentWebSocketClient.onMessage");
    }

    @Override
    public void onMessage(ByteBuffer byteBuffer) {
        // 接收服务器返回的消息
        byte[] dataContent = byteBuffer.array();
        ExternalMessage message = DataCodecKit.decode(dataContent, ExternalMessage.class);
        log.info("与服务器连接成功");
        byte[] data = message.getData();
        if (data != null) {
            ConnectVerify helloReq = DataCodecKit.decode(data, ConnectVerify.class);
            log.info("\n{}", helloReq);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        System.out.println("CommentWebSocketClient.onClose");
    }

    @Override
    public void onError(Exception e) {
        System.out.println("CommentWebSocketClient.onError");
    }
}
