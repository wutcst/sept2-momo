/**
 * 该类是"房间"类，它描述了房间的属性和方法
 * 房间的属性包括房间的描述、房间的出口
 * 房间的方法包括获取房间的详细/简略描述、获取房间的出口、设置房间的出口、从某个出口去往下一个房间
 * 通过lombok注解自动生成getter方法
 *
 * 修改内容：
 * 1. 添加了name属性，用于存储房间的名称
 * 2. 修改了构造函数，支持初始化name和description属性
 * 3. 修改了getLongDescription方法，使其输出包含房间名称和出口信息
 * 4. 修改了getExitString方法，使其返回的出口字符串更加清晰
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
     * 创建一个房间对象，需要指定房间的名称和描述
     * @param name        房间的名称
     * @param description 房间的描述
     */
    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        exits = new HashMap<>();
    }

    /**
     * 设置房间的出口
     * @param direction 出口的方向
     * @param neighbor  出口所在的房间
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
     * 获取房间的详细描述，包括房间名称和出口信息
     * @return 房间的详细描述
     */
    public String getLongDescription() {
        StringBuilder longDescription = new StringBuilder();
        if (name != null) {
            longDescription.append("You are ").append(name).append(".\n");
        }
        longDescription.append(description).append("\n").append(getExitString());
        return longDescription.toString();
    }

    /**
     * 获取房间的出口信息
     * @return 房间的出口信息
     */
    private String getExitString() {
        StringBuilder exitString = new StringBuilder("Exits:");
        Set<String> exitDirections = exits.keySet();
        for (String direction : exitDirections) {
            exitString.append(" ").append(direction);
        }
        return exitString.toString();
    }

    /**
     * 根据出口方向获取下一个房间
     * @param direction 出口的方向
     * @return 下一个房间
     */
    public Room getExit(String direction) {
        return exits.get(direction);
    }
}
