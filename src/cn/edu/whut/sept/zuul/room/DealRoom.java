package cn.edu.whut.sept.zuul.room;

import cn.edu.whut.sept.zuul.User;

/**.
 * 继承自Room类,表示交易房间
 */
public class DealRoom extends Room {
    /**
     * @param description 描述
     * @param name 名字
     */
    public DealRoom(final String description, final String name) {
        super(description, name);
    }

    /**.
     * 返回房间信息,提示用户执行交易动作
     */
    @Override
    public void events(final User user) {
        System.out.println("Your now score points is "
                + user.sumPrice() + ", deal?");
    }
}
