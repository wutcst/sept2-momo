package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.SqlService.MysqlService;
import cn.edu.whut.sept.zuul.User;

/**.
 * 该类继承自Manner
 * 表示交易动作
 */
public class MannerDeal extends Manner {
    private User user;

    public MannerDeal(final CommandWord commandWord, final User aUser) {
        super(commandWord);
        this.user = aUser;
    }

    /**
     * @param command 命令
     * @return 成功处理 true
     */
    @Override
    public boolean manageCommand(final Command command) {
        //如果在交易所,进行交易
        if (user.getCurrentRoom().getName().equals("exchange")) {
            System.out.println("your score is " + user.sumPrice());
            MysqlService.saveScore(user);
            return true;
        }
        System.out.println("you are not in exchange room,you can't make deal");
        return false;
    }
}
