/**
 * @description : [游戏配置类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-22 21:11]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-22 21:11]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.util;



public interface GameConfig {
    /** 对外服务器 port */
    int externalPort = 10100;
    /** 对外服务器 ip */
    String externalIp = "127.0.0.1";
    /** http 升级 websocket 协议地址 */
    String websocketPath = "/websocket";
}
