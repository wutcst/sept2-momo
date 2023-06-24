/**
 * @description : [游戏玩家组件]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-23 9:29]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 22:56]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.components;

import com.almasb.fxgl.entity.component.Component;
import com.xf.woz.net.onMessage.*;
import com.xf.woz.pojo.Player;
import com.xf.woz.protoBuf.Command;

import java.util.HashMap;
import java.util.Map;

public class PlayerView extends Component {
    private Map<String, Runnable> onMessageMap;

    public PlayerView() {
        onMessageMap = new HashMap<>();
        onMessageMap.put("go", () -> GoOnMessage.me().request("go"));
        onMessageMap.put("userLoginVerify", () -> UserLoginVerifyOnMessage.me().request("userLoginVerify"));
        onMessageMap.put("help", () -> HelpOnMessage.me().request("help"));
        onMessageMap.put("items", () -> ItemsOnMessage.me().request(new Command("items", null)));
        onMessageMap.put("look", () -> LookOnMessage.me().request("look"));
        onMessageMap.put("quit", () -> QuitOnMessage.me().request("quit"));
        onMessageMap.put("take", () -> TakeOnMessage.me().request("take"));
        onMessageMap.put("drop", () -> DropOnMessage.me().request("drop"));
        onMessageMap.put("back", () -> BackOnMessage.me().request("back"));
    }

    public void login(Player player) {
        UserLoginVerifyOnMessage.me().request(player);
    }

    public void command(String firstCommand) {
        if (onMessageMap.containsKey(firstCommand)) {
            onMessageMap.get(firstCommand).run();
        } else {
            System.out.println("Invalid command!");
        }
    }

    public void items() {
        ItemsOnMessage.me().request(new Command("items", null));
    }
}
