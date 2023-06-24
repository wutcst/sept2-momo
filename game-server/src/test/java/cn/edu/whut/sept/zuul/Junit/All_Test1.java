import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ZuulGameTest {
    private Game game;

    @Before
    public void setUp() {
        game = new Game();
    }

    @Test
    public void testPlay() {
        // 模拟用户输入命令
        String input = "go north\nquit\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        // 重定向标准输出到字符串缓冲区
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        // 调用play()方法运行游戏
        game.play();

        // 恢复标准输入和标准输出
        System.setIn(System.in);
        System.setOut(System.out);

        // 验证输出结果是否正确
        String expectedOutput = "Welcome to the World of Zuul!\n\n" +
                "You are in the lobby\n" +
                "Exits: north west\n" +
                "> " +
                "You have successfully quit the game. Goodbye!\n";
        assertEquals(expectedOutput, outputStream.toString());
    }
}
