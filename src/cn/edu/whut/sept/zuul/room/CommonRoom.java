package cn.edu.whut.sept.zuul.room;

import cn.edu.whut.sept.zuul.User;

/**.
 * 继承自Room类,表示普通房间
 */
public class CommonRoom extends Room {
    /**
     * @param description 描述
     * @param name 名字
     */
    public CommonRoom(final String description, final String name) {
        super(description, name);
    }

    /**.
     * 无事发生
     */
    @Override
    public void events(final User user) {
        System.out.println("It's a common room");
    }
}
