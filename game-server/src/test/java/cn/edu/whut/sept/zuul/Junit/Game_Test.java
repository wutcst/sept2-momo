import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GameTest {
    private Game game;
    private ByteArrayOutputStream output;

    @Before
    public void setUp() {
        game = new Game();
        output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));
    }

    @Test
    public void testPrintWelcome() {
        game.printWelcome();
        String expectedOutput = "\nWelcome to the World of Zuul!\n" +
                "World of Zuul is a new, incredibly boring adventure game.\n" +
                "Type 'help' if you need help.\n\n" +
                "You are outside the main entrance of the university.\nExits: east south west";
        assertEquals(expectedOutput, output.toString().trim());
    }

    @Test
    public void testProcessCommand_UnknownCommand() {
        String input = "unknown";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Command command = game.getParser().getCommand();
        boolean finished = game.processCommand(command);

        assertTrue(output.toString().contains("I don't know what you mean..."));
        assertEquals(false, finished);
    }

    @Test
    public void testProcessCommand_HelpCommand() {
        String input = "help";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Command command = game.getParser().getCommand();
        boolean finished = game.processCommand(command);

        assertTrue(output.toString().contains("Your command words are:"));
        assertEquals(false, finished);
    }

    @Test
    public void testProcessCommand_GoCommand() {
        String input = "go east";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Command command = game.getParser().getCommand();
        boolean finished = game.processCommand(command);

        assertTrue(output.toString().contains("in a lecture theater"));
        assertEquals(false, finished);
    }

    @Test
    public void testProcessCommand_QuitCommand() {
        String input = "quit";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        Command command = game.getParser().getCommand();
        boolean finished = game.processCommand(command);

        assertTrue(output.toString().contains("Thank you for playing.  Good bye."));
        assertEquals(true, finished);
    }
}
