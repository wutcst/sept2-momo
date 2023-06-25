/**
 * @description : [help命令的onmessage]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-24 15:52]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 22:56]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.net.onMessage;

import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.xf.woz.net.onMessage.hallAndCmd.CmdModule;
import com.xf.woz.net.UserOnMessage;
import com.xf.woz.net.onMessage.hallAndCmd.UserCmd;
import com.xf.woz.protoBuf.ConnectVerify;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HelpOnMessage implements UserOnMessage {
    @Override
    public int getCmdMerge() {
        return CmdKit.merge(CmdModule.USER_ACTION, UserCmd.HELP);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {
        ConnectVerify connectVerify = DataCodecKit.decode(data, ConnectVerify.class);
        log.info("connectVerify-info:{}", connectVerify);
        return connectVerify;
    }

    public static HelpOnMessage me() {
        return Holder.ME;
    }

    /**
     * 通过 JVM 的类加载机制, 保证只加载一次 (singleton)
     */
    private static class Holder {
        static final HelpOnMessage ME = new HelpOnMessage();
    }
}
