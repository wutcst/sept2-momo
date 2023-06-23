/**
 * @description : [根据当前客户端命令动态获取 GameCommand 实例]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-16 17:13]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 23:00]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.command;

import java.util.HashMap;
import java.util.Map;

public class GameCommandContext {
    private static final Map<String, GameCommand> commandMap = new HashMap<>();

    public GameCommandContext() {
        commandMap.put("help", new HelpCommand());
        commandMap.put("quit", new QuitCommand());
        commandMap.put("go", new GoCommand());
    }

    public static GameCommand getCommandInstance(String command) {
        return commandMap.get(command);
    }

}
