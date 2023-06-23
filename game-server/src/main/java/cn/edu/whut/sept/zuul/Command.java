/**
 * 该类是作为命令行语句的解析对象，用于解析命令行语句，将其分解为命令和参数
 *
 * @author Michael Kölling and David J. Barnes
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

public class Command {
    private String commandWord;
    private String secondWord;

    /**
     * 创建一个Command对象. 可以看到一个命令对象包含两个字符串，
     * 一个是命令单词本身，另一个是命令的第二个单词(命令参数).
     *
     * @param firstWord 命令单词
     * @param secondWord 命令参数
     */
    public Command(String firstWord, String secondWord) {
        commandWord = firstWord;
        this.secondWord = secondWord;
    }

    public String getCommandWord() {
        return commandWord;
    }

    public String getSecondWord() {
        return secondWord;
    }

    /**
     * 判断命令本身是否存在.
     * @return 如果命令单词为空，返回true，否则返回false.
     */
    public boolean isUnknown() {
        return (commandWord == null);
    }

    /**
     * 判断命令是否包含第二个单词.
     * @return 如果第二个单词不为空，返回true，否则返回false.
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}
