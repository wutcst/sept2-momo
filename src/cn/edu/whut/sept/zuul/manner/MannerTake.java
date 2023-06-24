package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.User;

/**.
 * 该类继承自Manner
 * 表示拿取房间物品动作
 */
public class MannerTake extends Manner {
    private User user;

    public MannerTake(final CommandWord commandWord, final User user) {
        super(commandWord);
        this.user = user;
    }

    /**
     * @param command 命令
     * @return 成功处理 true
     */
    @Override
    public boolean manageCommand(final Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Take what?");
            return false;
        }
        if (command.getIntWord() == 0) {
            System.out.println("Quantity error");
            return false;
        }
        System.out.println(
                user.take(command.getSecondWord(), command.getIntWord()));
        return false;
    }
}
