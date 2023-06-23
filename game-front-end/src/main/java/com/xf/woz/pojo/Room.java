/**
 * 该类是"房间"类，它描述了房间的属性和方法
 * 房间的属性包括房间的描述、房间的出口
 * 房间的方法包括获取房间的详细/简略描述、获取房间的出口、设置房间的出口、从某个出口去往下一个房间
 *
 * @author Michael Kölling and David J. Barnes
 * @version 1.0
 */
package com.xf.woz.pojo;

import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


@NoArgsConstructor

public class Room {
    @Getter
    @Protobuf
    private String name;
    private String description;
    private Map<String, Room> exits;

    /**
     * 创建一个房间对象，需要指定房间的描述
     * @param description 房间的描述
     */
    public Room(String name, String description) {
        this.description = description;
        this.name = name;
        exits = new HashMap<>();
    }

    /**
     * 设置房间的出口
     * @param direction 出口的方向
     * @param neighbor 出口所在的房间
     */
    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**
     * 获取房间的简短描述
     * @return 房间的简短描述
     */
    public String getShortDescription() {
        return description;
    }

    /**
     * 获取房间的详细描述
     * @return 房间的详细描述
     */
    public String getLongDescription() {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * 获取房间的出口
     * @return 房间的出口
     */
    private String getExitString() {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString += " " + exit;
        }
        return returnString;
    }

    /**
     * 从某个出口去往下一个房间
     * @param direction 出口的方向
     * @return 下一个房间
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }
}


