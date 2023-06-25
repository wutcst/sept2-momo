package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.User;

/**.
 * 该类继承自Manner
 * 表示吃饼干动作
 */
public class MannerEat extends Manner {
    private User user;

    public MannerEat(final CommandWord commandWord, final User aUser) {
        super(commandWord);
        this.user = aUser;
    }

    /**
     * @param command 命令
     * @return 成功处理 true
     */
    @Override
    public boolean manageCommand(final Command command) {
        if (user.eatCookie()) {
            System.out.println("Eat cookie successfully");
        } else {
            System.out.println("You don't have cookie");
        }
        return false;
    }
}
