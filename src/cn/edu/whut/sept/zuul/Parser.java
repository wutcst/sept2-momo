package cn.edu.whut.sept.zuul;

import java.util.Scanner;

/**.
 * 该类是“World-of-Zuul”应用程序中解析器
 * 其接受用户的输入,并将用户的输入处理后赋给command类并返回
 */
public class Parser {
    private CommandWords commands;
    private Scanner reader;

    /**.
     * 初始化其属性
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**.
     * 读取和处理用户输入的指令,并新建command对象进行接受
     *
     * @return 新建储存输入指令的command对象
     */
    public Command getCommand() {
        String inputLine;
        String word1 = null;
        String word2 = null;
        int word3;
        System.out.print("> ");

        inputLine = reader.nextLine();
        // 得到输入的单词,只得到改行的前两个单词
        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) { //第一个单词
            word1 = tokenizer.next();
            if (tokenizer.hasNext()) { //第二个单词
                word2 = tokenizer.next();
            }
        }
        //判断是否为已定义指令,如果不是赋值null
        if (commands.isCommand(word1)) {
            //如果下个输入为数字赋值该数字,否则为0
            if (tokenizer.hasNextInt()) {
                word3 = tokenizer.nextInt();
                return new Command(commands.getCommandWord(word1),
                        word2, word3);
            }
            return new Command(commands.getCommandWord(word1), word2, 0);
        } else {
            return new Command(null, word2, 0);
        }
    }

    /**
     * @return 得到的命令
     */
    public String[] getCommands() {
        return commands.getCommandList();
    }
}
