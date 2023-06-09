/**
 * @description : [go命令执行实例]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-16 17:11]

 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 23:00]

 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.command;

import cn.edu.whut.sept.zuul.Game;

public class GoCommand implements GameCommand {

    @Override
    public void execute(Game game) {
        System.out.println("GoCommand");
    }

}
