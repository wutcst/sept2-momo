package cn.edu.whut.sept.zuul;

import java.util.HashMap;

/**.
 * 该类是“World-of-Zuul”应用程序中储存已定义指令的类
 * 储存和处理已定义的指令
 */
public class CommandWords {
    /**.
     * 属性  validCommands:语句和命令的对应关系
     */
    private HashMap<String, CommandWord> validCommands;

    /**.
     * 初始化语句和命令的对应关系
     */
    public CommandWords() {
        validCommands = new HashMap<>();
        validCommands.put("go", CommandWord.GO);
        validCommands.put("quit", CommandWord.QUIT);
        validCommands.put("help", CommandWord.HELP);
        validCommands.put("eat", CommandWord.EAT);
        validCommands.put("take", CommandWord.TAKE);
        validCommands.put("drop", CommandWord.DROP);
        validCommands.put("deal", CommandWord.DEAL);
        validCommands.put("view", CommandWord.VIEW);
        validCommands.put("back", CommandWord.BACK);
        validCommands.put("save", CommandWord.SAVE);
    }

    /**.
     * 判断用户输入的是否为指令
     *
     * @param aString 用户输入的单词
     * @return 如果为指令返回true, 否则返回false
     */
    public boolean isCommand(final String aString) {
        CommandWord commandWord = validCommands.get(aString);
        if (commandWord == null) {
            return false;
        }
        return true;
    }

    /**
     * @return 已有命令
     */
    public String[] getCommandList() {
        return validCommands.keySet().toArray(new String[0]);
    }

    /**
     * @param aString 命令
     * @return enum值
     */
    public CommandWord getCommandWord(final String aString) {
        return validCommands.get(aString);
    }
}
