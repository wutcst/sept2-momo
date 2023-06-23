/**
 * @description : [游戏命令接口类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-16 17:08]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 23:00]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.command;

import cn.edu.whut.sept.zuul.Game;

public interface GameCommand {
    /**
     * 执行命令
     * @param game 游戏实例
     */
    void execute(Game game);
}
