package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.User;

/**.
 * 该类继承自Manner
 * 表示查看用户信息或者房间信息动作
 */
public class MannerView extends Manner {
    private User user;

    public MannerView(final CommandWord commandWord, final User aUser) {
        super(commandWord);
        this.user = aUser;
    }

    /**
     * @param command 命令
     * @return 成功处理 true
     */
    @Override
    public boolean manageCommand(final Command command) {
        if (!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("View what?");
            return false;
        }

        String purpose = command.getSecondWord();
        //查看用户
        if (purpose.equals("user")) {
            System.out.println(user.view());
            //查看房间
        } else if (purpose.equals("room")) {
            System.out.println(user.getCurrentRoom().getLongDescription());
        } else {
            System.out.println("You can only view user and room");
        }
        return false;
    }
}
