package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**.
 * 这是Manner单元测试类
 */
class MannerTest {
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game("test");
    }

    @Test
    void manageCommand() {
        Command commandGo = new Command(CommandWord.GO, "west", 0);
        game.processCommand(commandGo);

        Command commandTake = new Command(CommandWord.TAKE, "chair", 2);
        Command commandTakeError = new Command(CommandWord.TAKE, "chai", 1);
        Command commandTakeError2 = new Command(CommandWord.TAKE, "chair", 0);
        //正常拿走指令
        game.processCommand(commandTake);
        //物品名输入错误指令
        game.processCommand(commandTakeError);
        //拿走数量错误指令
        game.processCommand(commandTakeError2);

        Command commandDrop = new Command(CommandWord.DROP, "chair", 1);
        Command commandDropError = new Command(CommandWord.DROP, "chai", 1);
        Command commandDropError2 = new Command(CommandWord.DROP, "chair", 0);
        //正常放置
        game.processCommand(commandDrop);
        //放置物品名错误
        game.processCommand(commandDropError);
        //数量错误
        game.processCommand(commandDropError2);

        Command commandView = new Command(CommandWord.VIEW, "room", 0);
        Command commandView1 = new Command(CommandWord.VIEW, "user", 0);
        Command commandViewError = new Command(CommandWord.VIEW, null, 0);
        Command commandViewError1 = new Command(CommandWord.VIEW, "hoom", 0);
        //正常查看房间和用户
        game.processCommand(commandView);
        game.processCommand(commandView1);
        //未输入指定对象
        game.processCommand(commandViewError);
        //输入指定对象错误
        game.processCommand(commandViewError1);
    }
}
