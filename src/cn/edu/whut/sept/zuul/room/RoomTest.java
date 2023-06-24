package cn.edu.whut.sept.zuul.room;

import cn.edu.whut.sept.zuul.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**.
 * 这是room单元测试类
 */
class RoomTest {
    private Room room;
    private Item item1;
    private Item item2;

    @BeforeEach
    void setUp() {
        room = new CommonRoom("12345", "test");
        item1 = new Item("book", 2, 5);
        item2 = new Item("chair", 8, 11);
        room.setItem(item1, 15);
        room.setItem(item2, 15);
    }

    @Test
    void setItem() {
        room.setItem(item1, 10);
        room.setItem(item2, 10);
        assertEquals(room.getItems().get(item1), 10);
        assertEquals(room.getItems().get(item2), 10);
    }

    @Test
    void dropItem() {
        room.dropItem(item1, 10);
        room.dropItem(item2, 0);
        assertEquals(room.getItems().get(item1), 25);
        assertEquals(room.getItems().get(item2), 15);
    }

    @Test
    void takeItem() {
        assertTrue(room.takeItem(item1, 10));
        assertFalse(room.takeItem(item2, 20));
        assertTrue(room.takeItem(item2, 15));
        assertEquals(room.getItems().get(item1), 5);
        assertEquals(room.getItems().get(item2), 0);
    }
}
