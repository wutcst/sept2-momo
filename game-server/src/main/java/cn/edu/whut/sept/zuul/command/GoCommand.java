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
    public void execute() {
        System.out.println("执行 go 命令");

        // 模拟移动到不同房间的逻辑
        String room = getRandomRoom();

        if (room != null) {
            System.out.println("你移动到了" + room + "房间。");
        } else {
            System.out.println("无法移动到该房间。");
        }

        // 可以添加更多的逻辑代码
    }

    private String getRandomRoom() {
        // 模拟随机选择房间的逻辑
        String[] rooms = {"厨房", "客厅", "卧室", "书房"};
        int randomIndex = (int) (Math.random() * rooms.length);
        return rooms[randomIndex];
    }

}
