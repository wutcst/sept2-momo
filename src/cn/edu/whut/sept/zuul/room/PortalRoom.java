package cn.edu.whut.sept.zuul.room;

import cn.edu.whut.sept.zuul.User;

import java.util.Vector;

/**.
 * 继承自Room类,表示传送房间
 */
public class PortalRoom extends Room {
    /**.
     * 房间名
     */
    private Vector<Room> rooms;

    /**
     * @param description 描述
     * @param aRooms 房间
     * @param name 名字
     */
    public PortalRoom(final String description,
                      final Vector<Room> aRooms, final String name) {
        super(description, name);
        this.rooms = aRooms;
    }

    /**.
     * 房间事件,随机传送
     */
    @Override
    public void events(final User user) {
        user.clearPath();
        //得到随机编号,最后编号为该房间,除去
        int index = (int) (Math.random() * (rooms.size() - 1));
        user.setPath(rooms.get(index));
        rooms.get(index).events(user);
        System.out.println("You were teleported to "
                + rooms.get(index).getName());
    }
}
