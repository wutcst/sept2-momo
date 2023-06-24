package cn.edu.whut.sept.zuul;

import cn.edu.whut.sept.zuul.SqlService.MysqlService;

import java.util.HashMap;
import java.util.Scanner;

/**.
 * 该类是“World-of-Zuul”应用程序的启动类
 * 该类提供主界面功能,包括启动新游戏,继续游戏,查看榜单等功能
 *
 * @author 张忠瑾
 * @version 1.0
 */
public class BeginGame {
    /**.
     * 开始新游戏
     */
    public void play() {
        System.out.println("enter your game name");
        String name = "";
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            name = scanner.next();
        }
        //如果数据库中有该用户名,说明该用户名已存在,否则可以开始游戏
        if (MysqlService.compareName(name)) {
            Game game = new Game(name);
            game.play();
        } else {
            System.out.println("username already exist");
        }
    }

    /**.
     * 继续游戏
     */
    public void keepUp() {
        System.out.println("Enter the name of the game you want to continue");
        String name = "";
        Scanner scanner = new Scanner(System.in);
        if (scanner.hasNext()) {
            name = scanner.next();
        }
        //得到数据库恢复的游戏对象
        Game game = MysqlService.getGame(name);
        if (game != null) {
            game.play();
        }
    }

    /**.
     * 展示分数
     */
    public void showScore() {
        HashMap<String, Integer> score = MysqlService.score();
        System.out.println("username\tscore");
        if (score != null) {
            for (String name : score.keySet()) {
                System.out.println(name + "\t\t\t" + score.get(name));
            }
        }
    }

    /**.
     * 启动主界面
     */
    public void run() {
        int num = 0;
        while (num != 4) {
            System.out.println("You can enter this number");
            System.out.println("1.new game\n2.continue\n3.score\n4.exit");
            Scanner scanner = new Scanner(System.in);
            if (scanner.hasNextInt()) {
                num = scanner.nextInt();
            }
            switch (num) {
                case 1:
                    play();
                    break;
                case 2:
                    keepUp();
                    break;
                case 3:
                    showScore();
                    break;
                case 4:
                    System.out.println("bye");
                    break;
                default:
                    System.out.println("Input error");
            }
        }
    }
}
