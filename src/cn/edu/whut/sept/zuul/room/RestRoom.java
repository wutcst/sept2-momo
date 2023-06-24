package cn.edu.whut.sept.zuul.room;

import cn.edu.whut.sept.zuul.User;

/**.
 * 继承自Room类,表示恢复体力房间
 */
public class RestRoom extends Room {
    private boolean symbol = true;
    /**.
     * @param description 描述
     * @param name 名字
     */
    public RestRoom(final String description, final String name) {
        super(description, name);
    }

    /**.
     * 房间事件,恢复体力
     */
    @Override
    public void events(final User user) {
        if (symbol) {
            System.out.println("You sleep");
            user.changePower(2);
            symbol = false;
        }
    }
}
