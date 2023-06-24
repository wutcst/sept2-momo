package cn.edu.whut.sept.zuul;

import cn.edu.whut.sept.zuul.room.Room;

import java.util.HashMap;
import java.util.Stack;

/**.
 * 这个类是“World-of-Zuul”应用程序的用户类
 * 它用来储存玩家信息，并执行玩家相应的动作
 * 属性  path:玩家经过的路径,name:玩家名称，strength：玩家能拿走物体的重量，power：玩家可以移动的距离，items：玩家拥有的物体
 *
 * @author 张忠瑾
 * @version 1.0
 */
public class User {
    private Stack<Room> path;
    private String name;
    private int strength;
    private int power;
    private HashMap<Item, Integer> items;

    /**.
     * 初始化用户对象
     *
     * @param aName     玩家名称
     * @param room     玩家初始房间
     */
    public User(final String aName, final Room room) {

        path = new Stack<>();
        this.name = aName;
        strength = ConstFile.STRENGTH;
        power = ConstFile.POWER;
        path.push(room);
        items = new HashMap<>();
    }

    /**.
     * 增加玩家移动路径,更改玩家目前所在房间
     *
     * @param room 玩家移动到的房间
     */
    public void setPath(final Room room) {
        path.push(room);
    }

    /**.
     * @param item Item对象
     * @param num 数量
     */
    public void setItems(final Item item, final int num) {
        items.put(item, num);
    }

    /**.
     * @return 得到名字
     */
    public String getName() {
        return name;
    }

    /**.
     * @return 力量
     */
    public int getStrength() {
        return strength;
    }

    /**.
     * @return 体力
     */
    public int getPower() {
        return power;
    }

    /**.
     * @param aName 名字
     */
    public void setName(final String aName) {
        this.name = aName;
    }

    /**.
     * @param aStrength 力量
     */
    public void setStrength(final int aStrength) {
        this.strength = aStrength;
    }

    /**
     * @param aPower 体力
     */
    public void setPower(final int aPower) {
        this.power = aPower;
    }

    /**.
     * @return Item与数量组成的map
     */
    public HashMap<Item, Integer> getItems() {
        return items;
    }

    /**.
     * @return 玩家目前所在房间对象
     */
    public Room getCurrentRoom() {
        return path.peek();
    }

    /**.
     * 清楚玩家移动路径
     */
    public void clearPath() {
        path.clear();
    }

    /**.
     * 更改玩家目前体力
     *
     * @param num 更改的值,为正表示增加,为负表示减少体力
     */
    public void changePower(final int num) {
        power += num;
    }

    /**.
     * 返回动作,玩家返回到上个经过的房间
     *
     * @return 执行输出字符串
     */
    public String back() {
        //当体力过小
        if (power < 1) {
            return "You have low energy,can't move";
        }
        //没有经过的房间
        if (path.size() <= 1) {
            return "Nowhere to back";
        }
        power -= 1;
        path.pop();
        path.peek().events(this);
        return "Successful return";
    }

    /**.
     * 统计玩家拥有物品的价值
     *
     * @return 玩家拥有物品的价格
     */
    public int sumPrice() {
        int sum = 0;
        //遍历玩家拥有的物品
        for (Item key : items.keySet()) {
            sum += items.get(key) * key.getPrice();
        }
        return sum;
    }

    /**.
     * 统计玩家剩余能拿去物品的空间
     *
     * @return 玩家剩余能拿去物品的空间
     */
    public int getResidueStreagth() {
        int sum = 0;
        for (Item key : items.keySet()) {
            sum += items.get(key) * key.getWeigtht();
        }
        return strength - sum;
    }

    /**.
     * 玩家吃饼干动作,消耗饼干后,可多携带20重量的物体
     *
     * @return 如果成功吃饼干返回true, 否则返回false
     */
    public boolean eatCookie() {
        for (Item key : items.keySet()) {
            //如果玩家拥有饼干并且数量大于0
            if (key.getName().equals("cookie") && items.get(key) > 0) {
                strength += ConstFile.COOKIEHELP;
                items.put(key, items.get(key) - 1);
                return true;
            }
        }
        return false;
    }

    /**.
     * 玩家拿去房间内有的物品
     *
     * @param aName 拿走的物品名字
     * @param num  拿走的物品数量
     * @return 执行结果字符串
     */
    public String take(final String aName, final int num) {
        //得到房间中该名字的物品对象
        Item item = path.peek().getItem(aName);

        if (item == null) {
            return "There are no such items in the room";
        }
        //如果玩家剩余空间无法拿走物品
        if (getResidueStreagth() < item.getWeigtht() * num) {
            return "Lack of strength";
        }
        //房间执行其拿走方法,房间物体减少
        if (path.peek().takeItem(item, num)) {
            //增加玩家物品数
            if (items.get(item) == null) {
                items.put(item, num);
            } else {
                items.put(item, items.get(item) + num);
            }
            return "Take away successfully";
        }
        return "There are no such items in the room";
    }

    /**.
     * @param aName 物品名字
     * @return 玩家该名字的物品对象
     */
    public Item getItem(final String aName) {
        for (Item key : items.keySet()) {
            //当名字匹配,返回该对象
            if (key.getName().equals(aName)) {
                return key;
            }
        }
        return null;
    }

    /**.
     * 放置物品动作,将玩家拥有的此物品放到当前房间中
     *
     * @param aName 放置物品名字
     * @param num  放置物品数量
     * @return 动作执行结果字符串
     */
    public String drop(final String aName, final int num) {
        Item item = getItem(aName);
        //当物品为空或者数量过少,报错
        if (item == null || items.get(item) < num) {
            return "You don't have this items";
        }
        //房间也执行相应的动作,增加物品
        path.peek().dropItem(item, num);
        items.put(item, items.get(item) - num);
        return "Drop out successfully";
    }

    /**.
     * 玩家执行进入下一个房间动作,消耗体力
     *
     * @param direction 下一个房间方向
     * @return 执行结果字符串
     */
    public String go(final String direction) {
        //体力过少,无法移动
        if (power < 1) {
            return "You have low energy,can't move";
        }
        Room nextRoom = path.peek().getExit(direction);
        if (nextRoom == null) {
            return "There is no door!";
        } else {
            //玩家移动,体力减少,并执行房间特殊事件
            power -= 1;
            path.push(nextRoom);
            path.peek().events(this);
            return "";
        }
    }

    /**
     * @return 玩家信息字符串
     */
    public String view() {
        return "your strength have " + getResidueStreagth()
                + "\nyour power is " + power + "\nyou have\n" + viewItem();
    }

    /**
     * @return 玩家拥有物品信息字符串
     */
    public String viewItem() {
        String detail = "";
        detail += "name" + "\t" + "number" + "\t" + "weigth\t" + "price\n";
        for (Item key : items.keySet()) {
            if (items.get(key) > 0) {
                detail += key.getName() + "\t" + items.get(key) + "\t\t"
                        + key.getWeigtht() + "\t\t" + key.getPrice() + "\n";
            }
        }
        if (detail.equals("")) {
            return "nothing";
        }
        return detail;
    }
}
