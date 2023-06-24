package cn.edu.whut.sept.zuul;

/**.
 * 该类是“World-of-Zuul”应用程序中处理用户输入的类
 * 该类的实例将储存用户输入的单词
 * 对用户输入的单词进行处理
 * 属性  commandWord:指令单词,secondWord:第二个单词,thirdWord:第三个输入的数字
 */
public class Command {
    private CommandWord commandWord;
    private String secondWord;
    private int thirdWord;

    /**.
     * 将用户输入的值赋给内部数据
     *
     * @param firstWord  用户输入的第一个单词
     * @param aSecondWord 用户输入的第二个单词
     * @param sThirdWord  用户输入的第三个单词
     */
    public Command(final CommandWord firstWord,
                   final String aSecondWord, final int sThirdWord) {
        commandWord = firstWord;
        this.secondWord = aSecondWord;
        this.thirdWord = sThirdWord;
    }

    /**.
     * 得到指令单词
     *
     * @return 用户输入的指令单词
     */
    public CommandWord getCommandWord() {
        return commandWord;
    }

    /**.
     * 得到第二个单词
     *
     * @return 用户输入的第二个单词
     */
    public String getSecondWord() {
        return secondWord;
    }

    /**
     * @return 第三个数字
     */
    public int getIntWord() {
        return thirdWord;
    }

    /**.
     * 判断指令单词与已定义的指令是否匹配
     *
     * @return 不匹配返回true, 匹配返回false
     */
    public boolean isUnknown() {
        return (commandWord == null);
    }

    /**.
     * 判断是否有第二个单词
     *
     * @return 如果有返回true, 没有返回false
     */
    public boolean hasSecondWord() {
        return (secondWord != null);
    }
}
