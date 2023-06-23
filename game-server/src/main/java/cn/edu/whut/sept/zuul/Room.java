/**
 * 该类是"房间"类，它描述了房间的属性和方法
 * 房间的属性包括房间的描述、房间的出口
 * 房间的方法包括获取房间的详细/简略描述、获取房间的出口、设置房间的出口、从某个出口去往下一个房间
 *
 * @author Michael Kölling and David J. Barnes
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Room {
    @Getter
    private String name;
    private String description;
    private Map<String, Room> exits;

    /**
     * 创建一个房间对象，需要指定房间的描述
     * @param description 房间的描述
     */
    public Room(String description) {
        this.description = description;
        exits = new HashMap<>();
    }

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
        StringBuilder returnString = new StringBuilder("Exits:");
        Set<String> keys = exits.keySet();
        for (String exit : keys) {
            returnString.append(" ").append(exit);
        }
        return returnString.toString();
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


