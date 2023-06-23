/**
 * @description : [游戏配置类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-22 21:10]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-22 21:10]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class WOZConfig {
    /** 对外服务器 port */
    public int externalPort = GameConfig.externalPort;
    /** 对外服务器 ip */
    public String externalIp = GameConfig.externalIp;
}
