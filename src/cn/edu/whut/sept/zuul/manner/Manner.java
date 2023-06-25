package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;

/**.
 * 该类是“World-of-Zuul”应用程序表示动作的抽象类
 * 其储存执行该动作需要的指令
 */
public abstract class Manner {
    private CommandWord commandWord;

    public Manner(final CommandWord aCommandWord) {
        this.commandWord = aCommandWord;
    }

    /**
     * @param aCommandWord 命令
     * @return 一样 true
     */
    public boolean compareCommand(final CommandWord aCommandWord) {
        return this.commandWord == aCommandWord;
    }

    /**
     * @param command 命令
     * @return 成功处理 true
     */
    public abstract boolean manageCommand(Command command);
}
