package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.User;

/**.
 * 该类继承自Manner
 * 表示返回上歌房间动作
 */
public class MannerBack extends Manner {
    private User user;

    public MannerBack(final CommandWord commandWord, final User aUser) {
        super(commandWord);
        this.user = aUser;
    }

    /**
     * @param command 命令
     * @return 成功处理 true
     */
    @Override
    public boolean manageCommand(final Command command) {
        System.out.println(user.back());
        System.out.println(user.getCurrentRoom().getLongDescription());
        return false;
    }
}
