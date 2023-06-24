/**
 * @description : [游戏服务器启动类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-17 19:35]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-17 19:35]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul;

import com.iohao.game.simple.SimpleHelper;

import java.util.List;

public class GameServer {


    public static void main(String[] args) {

        // 游戏对外服端口
        int port = 10100;

        // 逻辑服
        LogicServer demoLogicServer = new LogicServer();

        // 启动 对外服、网关服、逻辑服; 并生成游戏业务文档
        SimpleHelper.run(port, List.of(demoLogicServer));
    }
}
