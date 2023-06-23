import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class GameServerIntegrationTest {
    @Test
    public void testGameServerStartup() {
        // 设置测试用的端口号
        int testPort = 12345;

        // 创建逻辑服对象
        LogicServer logicServer = new LogicServer();

        // 创建游戏服务器对象
        GameServer gameServer = new GameServer();

        // 用测试端口号替换原有的端口号
        gameServer.setPort(testPort);

        // 启动游戏服务器
        gameServer.start();

        // 获取游戏服务器实际使用的端口号
        int actualPort = gameServer.getPort();

        // 验证游戏服务器是否成功启动，并且使用了正确的端口号
        assertTrue(gameServer.isRunning());
        assertEquals(testPort, actualPort);

        // 验证逻辑服是否已经启动
        assertTrue(logicServer.isRunning());

        // 停止游戏服务器
        gameServer.stop();

        // 验证游戏服务器和逻辑服是否已经停止
        assertFalse(gameServer.isRunning());
        assertFalse(logicServer.isRunning());
    }
}
