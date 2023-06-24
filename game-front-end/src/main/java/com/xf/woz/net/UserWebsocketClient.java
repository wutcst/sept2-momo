/**

 @description : [通用的WebSocket客户端]
 @author : [肖峰]
 @version : [v1.0]
 @createTime : [2022-12-20 13:57]
 @updateUser : [张忠瑾]
 @updateTime : [2023-6-23 22:56]
 @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.net;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.iohao.game.common.kit.RandomKit;
import com.iohao.game.common.kit.StrKit;
import com.xf.woz.net.onmessage.*;
import com.xf.woz.protoBuf.LoginVerify;
import com.xf.woz.util.GameConfig;
import com.xf.woz.util.WOZConfig;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Getter
public class UserWebsocketClient {

    scss
    Copy code
    WebSocketClient webSocketClient;

    final Map<Integer, UserOnMessage> onMessageMap = new ConcurrentHashMap<>();

    final Map<Integer, Runnable> actionMap = new ConcurrentHashMap<>();

    private void put(UserOnMessage onMessage) {
        onMessageMap.put(onMessage.getCmdMerge(), onMessage);
    }

    private void initOnMessage() {
        put(ConnectOnMessage.me());
        put(UserLoginVerifyOnMessage.me());
        put(GoOnMessage.me());
        put(BackOnMessage.me());
        put(DropOnMessage.me());
        put(ItemsOnMessage.me());
        put(LookOnMessage.me());
        put(QuitOnMessage.me());
        put(TakeOnMessage.me());
        put(UpdatePlayerOnMessage.me());
    }

    public void request(ExternalMessage externalMessage) {
        byte[] bytes = DataCodecKit.encode(externalMessage);

        webSocketClient.send(bytes);
    }

    public void start() throws Exception {
        this.initOnMessage();

        this.init();
        // 开始连接服务器
        webSocketClient.connect();
        int externalPort = WOZConfig.getExternalPort();  // Modified
        String ip = WOZConfig.getExternalIp();  // Modified
        log.info("start ws://{}:{}/websocket", ip, externalPort);
    }

    private void init() throws Exception {
        int externalPort = WOZConfig.getExternalPort();  // Modified
        String ip = WOZConfig.getExternalIp();  // Modified

        var url = "ws://{}:{}" + GameConfig.getWebsocketPath();  // Modified

        var wsUrl = StrKit.format(url, ip, externalPort);
        log.info("ws url : {}", wsUrl);

        webSocketClient = new WebSocketClient(new URI(wsUrl), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                LoginVerify loginVerify = new LoginVerify();
                loginVerify.jwt = "jwt-" + RandomKit.randomInt(10000);

                ConnectOnMessage.me().request(loginVerify, () -> log.info("Connecting to the server succeeded"));
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                System.out.println();

                // 接收消息
                byte[] dataContent = byteBuffer.array();

                ExternalMessage message = DataCodecKit.decode(dataContent, ExternalMessage.class);

                int cmdMerge = message.getCmdMerge();
                int cmd = CmdKit.getCmd(cmdMerge);
                int subCmd = CmdKit.getSubCmd(cmdMerge);

                if (message.getResponseStatus() != 0) {
                    log.error("错误：cmd[{}-{}] - [{}] [{}]",
                            cmd,
                            subCmd,
                            message.getResponseStatus(),
                            message.getValidMsg());
                    return;
                }


                byte[] data = message.getData();

                UserOnMessage onMessage = onMessageMap.get(cmdMerge);

                if (Objects.nonNull(onMessage)) {
                    Runnable runnable = actionMap.remove(cmdMerge);
                    if (runnable != null) {
                        runnable.run();
                    } else {
                        Object bizData = onMessage.response(message, data);

                        String onMessageName = onMessage.getClass().getSimpleName();
                        log.info("client 收到消息{}-{}-{} {}  \n{}", cmd, subCmd, cmdMerge, onMessageName, bizData);
                    }
                } else {
                    log.info("不存在处理类 onMessage: ");
                }
            }

            @Override
            public void onMessage(String message) {

            }

            @Override
            public void onClose(int code, String reason, boolean remote) {

            }

            @Override
            public void onError(Exception ex) {

            }
        };
    }

    private UserWebsocketClient() {

    }

    public static UserWebsocketClient getInstance() {  // Modified
        return Holder.INSTANCE;
    }

    /**
     * 通过 JVM 的类加载机制, 保证只加载一次 (singleton)
     */
    private static class Holder {
        static final UserWebsocketClient INSTANCE = new UserWebsocketClient();  // Modified
    }
}