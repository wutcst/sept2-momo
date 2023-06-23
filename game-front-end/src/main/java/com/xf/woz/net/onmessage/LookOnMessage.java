/**
 * @description : [look命令的onmessage]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-24 16:10]
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
import com.xf.woz.protoBuf.RoomProtoBuf;
import com.xf.woz.util.FXGLUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LookOnMessage implements UserOnMessage {
    @Override
    public int getCmdMerge() {
        return CmdKit.merge(CmdModule.USER_ACTION, UserCmd.LOOK);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {
        RoomProtoBuf roomProtoBuf = DataCodecKit.decode(data, RoomProtoBuf.class);

        log.info(roomProtoBuf.getItems().toString());
        FXGLUtils.setItems(roomProtoBuf.getName(),roomProtoBuf.getItems());
        return roomProtoBuf.getItems();
    }

    public static LookOnMessage me() {
        return Holder.ME;
    }

    /**
     * 通过 JVM 的类加载机制, 保证只加载一次 (singleton)
     */
    private static class Holder {
        static final LookOnMessage ME = new LookOnMessage();
    }
}
