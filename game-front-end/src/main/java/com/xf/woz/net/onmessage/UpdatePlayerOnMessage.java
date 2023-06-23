/**
 * @description : [保存玩家信息的OnMessage类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-25 10:34]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 22:56]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.net.onmessage;

import com.almasb.fxgl.dsl.FXGL;
import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.xf.woz.entityFactory.TalkFactory;
import com.xf.woz.net.UserOnMessage;
import com.xf.woz.pojo.Player;
import com.xf.woz.scene.TalkScene;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class UpdatePlayerOnMessage implements UserOnMessage {
    @Override
    public int getCmdMerge() {
        return CmdKit.merge(UserCmd.CMD, UserCmd.UPDATE);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {
        Player player = DataCodecKit.decode(data, Player.class);
        log.info("player-info:{}", player);
        FXGL.runOnce(() -> {
            TalkScene.getInstance().show(TalkFactory.buildTalkList(List.of("Data saved successfully")));
            System.exit(0);
        }, Duration.ONE);

        return player;
    }

    public static UpdatePlayerOnMessage me() {
        return Holder.ME;
    }

    /**
     * 通过 JVM 的类加载机制, 保证只加载一次 (singleton)
     */
    private static class Holder {
        static final UpdatePlayerOnMessage ME = new UpdatePlayerOnMessage();
    }
}
