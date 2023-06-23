/**
 * @description : [help命令的执行实例]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-16 17:06]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-16 17:06]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.command;

import cn.edu.whut.sept.zuul.Game;

public class HelpCommand implements GameCommand {
    @Override
    public void execute(Game game) {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        game.getParser().showCommands();
    }
}
