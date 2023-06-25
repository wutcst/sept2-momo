/**
 * @description : [保存房间信息的OnMessage类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-25 10:34]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 22:56]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.net.onMessage;


import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.xf.woz.net.UserOnMessage;
import com.xf.woz.net.onMessage.hallAndCmd.UserCmd;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UpdateAllRoomOnMessage implements UserOnMessage {
    @Override
    public int getCmdMerge() {
        return CmdKit.merge(UserCmd.CMD, UserCmd.UPDATE);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {

        return null;
    }

    public static UpdateAllRoomOnMessage me() {
        return Holder.ME;
    }

    /**
     * 通过 JVM 的类加载机制, 保证只加载一次 (singleton)
     */
    private static class Holder {
        static final UpdateAllRoomOnMessage ME = new UpdateAllRoomOnMessage();
    }
}
