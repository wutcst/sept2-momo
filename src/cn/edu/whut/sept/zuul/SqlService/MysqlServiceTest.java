package cn.edu.whut.sept.zuul.SqlService;

import cn.edu.whut.sept.zuul.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**.
 * 这是MysqlService单元测试类
 */
class MysqlServiceTest {

    @Test
    void getGame() {
        Game game = MysqlService.getGame("zzj");
        //查看恢复玩家信息,所在地点结果
        assertEquals(game.getUser().getCurrentRoom().getName(), "study");
        //查看恢复玩家拥有物品结果
        assertEquals(game.getUser().getItems().get(game.getItem("chair")), 1);
        //查看恢复房间信息结果
        assertEquals(
                game.getRoom("study").getItems().get(game.getItem("chair")), 1);
    }

    @Test
    void score() {
        //查看得到得分是否正确
        assertEquals(MysqlService.score().get("zzj"), 28);
    }
}
