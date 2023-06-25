/**
 * @description : [一句话描述该类的功能]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-26 15:23]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-24 15:23]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.net;

import com.xf.woz.net.onMessage.UpdateAllRoomOnMessage;
import com.xf.woz.net.onMessage.UpdatePlayerOnMessage;
import com.xf.woz.net.onMessage.UserLoginVerifyOnMessage;
import com.xf.woz.pojo.Player;
import com.xf.woz.util.FXGLUtils;
import org.junit.Test;

public class OnMessageTest {
    @Test
    public void UserLoginTest() {
        Player player = new Player();
        player.setName("qwe");
        player.setPwd("123456");
        UserLoginVerifyOnMessage.me().request(player);
    }

    @Test
    public void UpdateAllRoomTest() {
        UpdateAllRoomOnMessage.me().request(FXGLUtils.roomItems);
        //后续操作会自动调用reponse方法
    }

    @Test
    public void updatePlayerTest(){
        UpdatePlayerOnMessage.me().request(FXGLUtils.nowPlayer);
        //后续操作会自动调用reponse方法
    }
}
