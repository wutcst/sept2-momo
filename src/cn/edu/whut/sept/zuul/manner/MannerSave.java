package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.Game;
import cn.edu.whut.sept.zuul.SqlService.MysqlService;

/**.
 * 该类继承自Manner
 * 表示保存游戏动作
 */
public class MannerSave extends Manner {
    private Game game;

    public MannerSave(final CommandWord commandWord, final Game aGame) {
        super(commandWord);
        this.game = aGame;
    }

    /**
     * @param command 命令
     * @return 成功处理 true
     */
    @Override
    public boolean manageCommand(final Command command) {
        MysqlService.saveGame(game);
        return false;
    }
}
