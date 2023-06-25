package cn.edu.whut.sept.zuul.manner;

import cn.edu.whut.sept.zuul.Command;
import cn.edu.whut.sept.zuul.CommandWord;
import cn.edu.whut.sept.zuul.Parser;

/**.
 * 该类继承自Manner
 * 表示查询系统帮助
 */
public class MannerHelp extends Manner {
    public MannerHelp(final CommandWord commandWord) {
        super(commandWord);
    }

    /**
     * @param command 命令
     * @return 成功处理 true
     */
    @Override
    public boolean manageCommand(final Command command) {
        Parser parser = new Parser();
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        String[] commandList = parser.getCommands();
        for (String c : commandList) {
            System.out.print(c + "  ");
        }
        System.out.println();
        return false;
    }
}
