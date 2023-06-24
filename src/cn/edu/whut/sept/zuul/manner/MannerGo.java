package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.User;

/**.
 * 该类继承自Manner
 * 表示进入下个房间动作
 */
public class MannerGo extends Manner {
    /**.
     * 定义用户
     */
    private final User user;

    /**
     * @param commandWord 输入命令
     * @param aUser 用户
     */
    public MannerGo(final CommandWord commandWord, final User aUser) {
        super(commandWord);
        this.user = aUser;
    }

    /**
     * @param command 命令
     * @return false 表示不退出
     */
    @Override
    public boolean manageCommand(final Command command) {
        if (!command.hasSecondWord()) {
            System.out.println("Go where?");
            return false;
        }

        String direction = command.getSecondWord();

        System.out.println(user.go(direction));
        return false;
    }
}
