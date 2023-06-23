import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ParserTest {
    private Parser parser;

    @Before
    public void setUp() {
        parser = new Parser();
    }

    @Test
    public void testGetCommand() {
        // 模拟用户输入命令
        String input = "go north";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Command command = parser.getCommand();

        // 验证解析得到的命令对象是否正确
        assertEquals("go", command.getCommandWord());
        assertEquals("north", command.getSecondWord());
    }

    @Test
    public void testShowCommands() {
        // 重定向标准输出到字符串缓冲区
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // 调用showCommands()方法
        parser.showCommands();

        // 恢复标准输出
        System.setOut(System.out);

        // 验证输出结果是否正确
        String expectedOutput = "go  quit  help\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}
