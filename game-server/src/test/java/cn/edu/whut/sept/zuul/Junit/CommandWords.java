import org.junit.Test;

import static org.junit.Assert.*;

public class CommandWordsTest {
    @Test
    public void testIsCommand() {
        CommandWords commandWords = new CommandWords();

        // 测试合法命令
        assertTrue(commandWords.isCommand("go"));
        assertTrue(commandWords.isCommand("quit"));
        assertTrue(commandWords.isCommand("help"));

        // 测试非法命令
        assertFalse(commandWords.isCommand("run"));
        assertFalse(commandWords.isCommand("exit"));
        assertFalse(commandWords.isCommand("info"));
    }

    @Test
    public void testShowAll() {
        CommandWords commandWords = new CommandWords();

        // 重定向标准输出到字符串缓冲区
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // 调用showAll()方法
        commandWords.showAll();

        // 恢复标准输出
        System.setOut(System.out);

        // 验证输出结果是否正确
        String expectedOutput = "go  quit  help\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}
