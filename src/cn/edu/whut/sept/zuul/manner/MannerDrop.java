package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.User;

/**.
 * 该类继承自Manner
 * 表示放置物品动作
 */
public class MannerDrop extends Manner {
    private User user;

    public MannerDrop(final CommandWord commandWord, final User aUser) {
        super(commandWord);
        this.user = aUser;
    }

    /**
     * @param command 命令
     * @return 成功处理 true
     */
    @Override
    public boolean manageCommand(final Command command) {
        //未输入放置物品名
        if (!command.hasSecondWord()) {
            System.out.println("Drop what?");
            return false;
        }
        //未输入放置数量
        if (command.getIntWord() == 0) {
            System.out.println("Quantity error");
            return false;
        }
        System.out.println(user.drop(command.getSecondWord(),
                command.getIntWord()));
        return false;
    }
}
