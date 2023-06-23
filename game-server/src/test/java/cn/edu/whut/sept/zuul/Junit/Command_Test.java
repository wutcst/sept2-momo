import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class CommandTest {
    @Test
    public void testGetCommandWord() {
        Command command = new Command("go", "east");
        assertEquals("go", command.getCommandWord());
    }

    @Test
    public void testGetSecondWord() {
        Command command = new Command("go", "east");
        assertEquals("east", command.getSecondWord());
    }

    @Test
    public void testIsUnknown_True() {
        Command command = new Command(null, null);
        assertTrue(command.isUnknown());
    }

    @Test
    public void testIsUnknown_False() {
        Command command = new Command("go", null);
        assertFalse(command.isUnknown());
    }

    @Test
    public void testHasSecondWord_True() {
        Command command = new Command("go", "east");
        assertTrue(command.hasSecondWord());
    }

    @Test
    public void testHasSecondWord_False() {
        Command command = new Command("go", null);
        assertFalse(command.hasSecondWord());
    }
}
