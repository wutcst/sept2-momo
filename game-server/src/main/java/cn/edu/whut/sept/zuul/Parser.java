/**
 * 该类作为解析器，用于解析用户输入的命令
 *
 * @author Michael Kölling and David J. Barnes
 * @version 1.0
 */
package cn.edu.whut.sept.zuul;

import java.util.Scanner;

public class Parser {
    private CommandWords commands;
    private Scanner reader;

    /**
     * 创建一个Parser对象，初始化命令对象和Scanner对象
     */
    public Parser() {
        commands = new CommandWords();
        reader = new Scanner(System.in);
    }

    /**
     * 从命令行读取输入的命令，并将其解析为命令对象
     * @return 返回一个命令对象
     */
    public Command getCommand() {
        String inputLine;
        String word1 = null;
        String word2 = null;

        System.out.print("> ");

        inputLine = reader.nextLine();

        Scanner tokenizer = new Scanner(inputLine);
        if (tokenizer.hasNext()) {
            word1 = tokenizer.next();
            if (tokenizer.hasNext()) {
                word2 = tokenizer.next();
            }
        }

        if (commands.isCommand(word1)) {
            return new Command(word1, word2);
        } else {
            return new Command(null, word2);
        }
    }

    /**
     * 打印出所有的命令
     */
    public void showCommands() {
        commands.showAll();
    }
}
