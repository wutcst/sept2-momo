package cn.edu.whut.sept.zuul.room;

import cn.edu.whut.sept.zuul.Item;
import cn.edu.whut.sept.zuul.User;

import java.util.Set;
import java.util.HashMap;

/**.
 * 该类是“World-of-Zuul”应用程序表示房间的抽象类
 * 其储存房间信息并处理和房间有关的内容,并定义抽象的房间事件方法,由不同房间执行
 * 属性: name:房间名,description:该房间的描述,exits:该房间的出口方向和其对应的房间,items:房间拥有的物品
 *
 * @author 张忠瑾
 * @version 1.0
 */
public abstract class Room {
    private String name;
    private String description;
    private HashMap<String, Room> exits;
    private HashMap<Item, Integer> items;


    /**.
     * 初始化房间
     * @param description 房间描述
     * @param name 房间名
     */
    public Room(final String description, final String name) {
        this.description = description;
        this.name = name;
        exits = new HashMap<>();
        items = new HashMap<>();
    }

    /**
     * @return 名字
     */
    public String getName() {
        return name;
    }

    /**
     * @param item Item对象
     * @param num 数量
     */
    public void setItem(final Item item, final int num) {
        items.put(item, num);
    }

    /**.
     * 房间增加对应物品和数量
     * @param item 用户放置的物品对象
     * @param num 放置的数量
     */
    public void dropItem(final Item item, final int num) {
        if (items.get(item) == null) {
            items.put(item, num);
        } else {
            items.put(item, items.get(item) + num);
        }
    }

    /**.
     * 房间减少对应物品和数量
     * @param item 用户拿走的物品
     * @param num 数量
     * @return 拿走结果, true表示可以拿走, false不可拿走
     */
    public boolean takeItem(final Item item, final int num) {
        //如果房间没有物品或者物品数量过少
        if (item == null || items.get(item) < num) {
            return false;
        }
        items.put(item, items.get(item) - num);
        return true;
    }

    /**.
     * 定义房间的出口信息
     * @param direction 出口方向
     * @param neighbor 出口方向对应的房间
     */
    public void setExit(final String direction, final Room neighbor) {
        exits.put(direction, neighbor);
    }

    /**.
     * 得到房间具体描述
     * @return 房间描述和出口和定义和物品内容
     */
    public String getLongDescription() {
        return "You are in " + name + ":" + description + ".\n"
                + getExitString() + "\nthis room have\n" + viewItem();
    }

    /**.
     * 查看房间拥有的物品
     * @return 房间拥有的物品内容
     */
    public String viewItem() {
        String detail = "";
        detail += "name" + "\t" + "number" + "\t" + "weigth\t" + "price\n";
        for (Item key : items.keySet()) {
            if (items.get(key) > 0) {
                detail += key.getName() + "\t" + items.get(key)
                        + "\t\t" + key.getWeigtht() + "\t\t"
                        + key.getPrice() + "\n";
            }
        }
        if (detail.equals("")) {
            return "nothing";
        }
        return detail;
    }

    /**.
     * 得到房间出口信息
     * @return 出口信息描述
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
     * @param name 物品名
     * @return 品名对应物品对象
     */
    public Item getItem(final String name) {
        for (Item key : items.keySet()) {
            if (key.getName().equals(name)) {
                return key;
            }
        }
        return null;
    }

    /**
     * @return Item
     */
    public HashMap<Item, Integer> getItems() {
        return items;
    }

    /**.
     * 得到该方向对应的房间对象
     * @param direction 出口方向
     * @return 该方向对应的房间对象
     */
    public Room getExit(final String direction) {
        return exits.get(direction);
    }

    /**
     * @param user 进入房间的用户
     */
    public abstract void events(User user);
}



