/**
 * @description : [go命令的onmessage]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-20 13:58]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 22:56]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.net.onmessage;

import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.xf.woz.net.CmdModule;
import com.xf.woz.net.UserOnMessage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GoOnMessage implements UserOnMessage {
    @Override
    public int getCmdMerge() {
        return CmdKit.merge(CmdModule.USER_ACTION, UserCmd.GO);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {
        String newRoom = DataCodecKit.decode(data, String.class);
        log.info("newRoom-info:{}", newRoom);
        return newRoom;
    }

    public static GoOnMessage me() {
        return Holder.ME;
    }

    /**
     * 通过 JVM 的类加载机制, 保证只加载一次 (singleton)
     */
    private static class Holder {
        static final GoOnMessage ME = new GoOnMessage();
    }
}
