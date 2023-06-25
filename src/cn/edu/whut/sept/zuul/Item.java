package cn.edu.whut.sept.zuul;

/**.
 * 此类是“World-of-Zuul”中物品类,储存物品信息
 * 属性  name:物品名称.price:物品价格,weight:物品重量
 */
public class Item {
    private String name;
    private int price;
    private int weigtht;

    public Item(final String aName, final int aPrice, final int aWeigtht) {
        this.name = aName;
        this.price = aPrice;
        this.weigtht = aWeigtht;
    }

    /**
     * @return 名字
     */
    public String getName() {
        return name;
    }

    /**
     * @return 价格
     */
    public int getPrice() {
        return price;
    }

    /**
     * @return 重量
     */
    public int getWeigtht() {
        return weigtht;
    }

}
