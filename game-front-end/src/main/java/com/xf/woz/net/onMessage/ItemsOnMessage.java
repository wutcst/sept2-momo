/**
 * @description : [items命令的onmessage]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-24 16:10]
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
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class ItemsOnMessage implements UserOnMessage {
    @Override
    public int getCmdMerge() {
        return CmdKit.merge(CmdModule.USER_ACTION, UserCmd.ITEMS);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {
        List res = DataCodecKit.decode(data, List.class);
        List<String> items = (List<String>) res;
        log.info("connectVerify-info:{}", items);
        return items;
    }

    public static ItemsOnMessage me() {
        return Holder.ME;
    }

    private static class Holder {
        static final ItemsOnMessage ME = new ItemsOnMessage();
    }
}
