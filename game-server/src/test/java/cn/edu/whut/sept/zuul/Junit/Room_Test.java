import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class RoomTest {
    private Room room;
    private Room neighbor;

    @Before
    public void setUp() {
        room = new Room("Living room", "A spacious living room");
        neighbor = new Room("Kitchen", "A well-equipped kitchen");
    }

    @Test
    public void testSetExit() {
        room.setExit("north", neighbor);
        Map<String, Room> expectedExits = new HashMap<>();
        expectedExits.put("north", neighbor);

        assertEquals(expectedExits, room.getExits());
    }

    @Test
    public void testGetShortDescription() {
        String shortDescription = room.getShortDescription();
        assertEquals("A spacious living room", shortDescription);
    }

    @Test
    public void testGetLongDescription() {
        room.setExit("north", neighbor);
        String longDescription = room.getLongDescription();
        String expectedDescription = "You are A spacious living room.\nExits: north";
        assertEquals(expectedDescription, longDescription);
    }

    @Test
    public void testGetExit() {
        room.setExit("north", neighbor);
        Room exit = room.getExit("north");
        assertEquals(neighbor, exit);
    }

    @Test
    public void testGetNonExistentExit() {
        Room exit = room.getExit("north");
        assertNull(exit);
    }
}
