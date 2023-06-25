package cn.edu.whut.sept.zuul.room;

import cn.edu.whut.sept.zuul.User;

/**.
 * 继承自Room类,表示多消耗体力房间
 */
public class ConsumeRoom extends Room {
    /**
     * @param description 描述
     * @param name 名字
     */
    public ConsumeRoom(final String description, final String name) {
        super(description, name);
    }

    /**.
     * 多消耗一点体力
     */
    @Override
    public void events(final User user) {
        System.out.println("You feel tired");
        user.changePower(-1);
    }
}
