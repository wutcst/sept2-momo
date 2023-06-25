package cn.edu.whut.sept.zuul;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**.
 * 这是User单元测试类
 */
class UserTest {
    private User user;
    private Game game;

    @BeforeEach
    void setUp() {
        //为了节省代码，通过game建立游戏地图房间和物品
        game = new Game("test");
        user = game.getUser();
    }

    @Test
    void getCurrentRoom() {
        assertEquals(user.getCurrentRoom(), game.getRoom("door"));
    }

    @Test
    void go() {
        user.go("west");
        //测试是否走向另一个房间
        assertEquals(user.getCurrentRoom(), game.getRoom("study"));
        //测试没有路时,是否进行处理
        assertEquals(user.go("south"), "There is no door!");
        user.go("east");
        user.go("south");
        assertEquals(user.getCurrentRoom(), game.getRoom("garden"));
        user.go("north");
        user.go("south");
        user.go("north");
        //测试体力不够时,是否进行处理
        assertEquals(user.go("south"), "You have low energy,can't move");
    }

    @Test
    void back() {
        user.go("west");
        user.go("north");
        //测试正常返回
        assertEquals(user.back(), "Successful return");
        assertEquals(user.getCurrentRoom(), game.getRoom("study"));
        user.back();
        //测试没有返回路径时,是否正常处理
        assertEquals(user.getCurrentRoom(), game.getRoom("door"));
        assertEquals(user.back(), "Nowhere to back");
        assertEquals(user.getCurrentRoom(), game.getRoom("door"));
    }

    @Test
    void take() {
        user.go("west");
        user.take("book", 5);
        //测试房间没有物体时,是否处理
        assertEquals(user.take("chair", 3),
                "There are no such items in the room");
        user.take("chair", 2);
        //测试体力不够时,是否处理
        assertEquals(user.take("chair", 15), "Lack of strength");
        //观察运行最终结果是否正确
        assertEquals(user.getItems().get(user.getItem("book")), 5);
        assertEquals(user.getItems().get(user.getItem("chair")), 2);

    }

    @Test
    void drop() {
        user.go("west");
        user.take("book", 5);
        user.take("chair", 2);
        user.go("east");
        user.drop("book", 4);
        assertEquals(user.getItems().get(user.getItem("book")), 1);
        assertEquals(user.drop("book", 2), "You don't have this items");
    }
}
