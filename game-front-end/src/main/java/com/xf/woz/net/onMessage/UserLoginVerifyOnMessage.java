/**
 * @description : [玩家登录验证的OnMessage]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-20 13:58]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 22:56]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.net.onMessage;

import com.iohao.game.action.skeleton.core.CmdKit;
import com.iohao.game.action.skeleton.core.DataCodecKit;
import com.iohao.game.bolt.broker.client.external.bootstrap.message.ExternalMessage;
import com.xf.woz.entityFactory.TalkFactory;
import com.xf.woz.net.UserOnMessage;
import com.xf.woz.net.onMessage.hallAndCmd.UserCmd;
import com.xf.woz.pojo.Player;
import com.xf.woz.pojo.Talk;
import com.xf.woz.scene.TalkScene;
import com.xf.woz.util.FXGLUtils;
import javafx.application.Platform;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class UserLoginVerifyOnMessage implements UserOnMessage {
    @Override
    public int getCmdMerge() {
        return CmdKit.merge(UserCmd.CMD, UserCmd.LOGIN_VERIFY);
    }

    @Override
    public Object response(ExternalMessage externalMessage, byte[] data) {
        Player player = DataCodecKit.decode(data, Player.class);
        log.info("player-info:{}", player);

        List<String> place = player.getPlace();
        //获得place中最后一个元素
        place = place.subList(place.size() - 1, place.size());
        String nowPlace = place.get(0);

        FXGLUtils.updateRoom(nowPlace);

        List<String> lines = new ArrayList<>();
        lines.add("Welcome to " + nowPlace);


        List<Talk> talks = TalkFactory.buildTalkList(lines);
        TalkScene instance = TalkScene.getInstance();
        Platform.runLater(() -> instance.show(talks));

        FXGLUtils.nowPlayer = player;

        return player;
    }

    public static UserLoginVerifyOnMessage me() {
        return Holder.ME;
    }

    /**
     * 通过 JVM 的类加载机制, 保证只加载一次 (singleton)
     */
    private static class Holder {
        static final UserLoginVerifyOnMessage ME = new UserLoginVerifyOnMessage();
    }
}
