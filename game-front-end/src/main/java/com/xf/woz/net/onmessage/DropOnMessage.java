/**
 * @description : [drop命令的onmessage]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-24 16:10]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-24 16:10]
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
public class DropOnMessage implements UserOnMessage {
    @Override
    public int getCmdMerge() {
        return CmdKit.merge(CmdModule.USER_ACTION, UserCmd.DROP);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {
        String newRoom = DataCodecKit.decode(data, String.class);
        log.info("newRoom-info:{}", newRoom);
        return newRoom;
    }


    public static DropOnMessage me() {
        return Holder.ME;
    }

    /**
     * 通过 JVM 的类加载机制, 保证只加载一次 (singleton)
     */
    private static class Holder {
        static final DropOnMessage ME = new DropOnMessage();
    }
}
