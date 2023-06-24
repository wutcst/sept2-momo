/**
 * 该类主要作用是记录所有的命令，以及判断输入的命令是否合法
 * 同时通过showAll()方法显示所有的命令
 *
 * @author Michael Kölling and David J. Barnes
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class CommandWords {
    // 一个constant数组，用于存储所有的命令
    private static final String[] validCommands = {
            "go", "quit", "help"
    };

    public CommandWords() {
        // nothing to do at the moment...
    }

    /**
     * 判断输入的命令是否合法
     * @param aString 输入的命令
     * @return 如果合法，返回true，否则返回false
     */
    public boolean isCommand(String aString) {
        for (String validCommand : validCommands) {
            if (validCommand.equals(aString))
                return true;
        }
        return false;
    }

    /**
     * 显示所有的命令
     *
     */
    public void showAll() {
        for (String command : validCommands) {
            System.out.print(command + "  ");
        }
        System.out.println();
    }
}
