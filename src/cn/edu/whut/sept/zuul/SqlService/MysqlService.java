package cn.edu.whut.sept.zuul.SqlService;

import cn.edu.whut.sept.zuul.Game;
import cn.edu.whut.sept.zuul.Item;
import cn.edu.whut.sept.zuul.User;
import cn.edu.whut.sept.zuul.room.Room;

import java.sql.SQLException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import java.util.HashMap;

/**.
 * 该类是“World-of-Zuul”应用程序数据库服务类
 * 主要负责保存游戏和恢复游戏,显示分数的数据库操作
 *
 * @author 张忠瑾
 * @version 1.0
 */
public final class MysqlService {
    /**.
     * JDBC驱动
     */
    private static final String DBDRIVER = "com.mysql.cj.jdbc.Driver";
    /**.
     * 数据库连接,连接到zuul数据库,可进行更改
     */
    private static final String DBURL = "jdbc:mysql://localhost:3306/zuul";
    /**.
     * 用户名
     */
    private static final String DBUSER = "root";
    /**.
     * 密码
     */
    private static final String DBPASSWORD = "123456";
    private MysqlService() {
    }
    /**.
     * 和数据库进行连接
     * @return Connection对象
     */
    public static Connection getConnection() {
        Connection conn = null;             //声明一个连接对象
        try {
            Class.forName(DBDRIVER);        //注册驱动
            conn = DriverManager.getConnection(DBURL, DBUSER, DBPASSWORD);
        } catch (ClassNotFoundException e) {      //捕获驱动类无法找到异常
            e.printStackTrace();
        } catch (SQLException e) {                //捕获SQL异常
            e.printStackTrace();
        }
        return conn;
    }

    /**.
     * 关闭连接对象
     * @param conn 连接对象
     */
    public static void close(final Connection conn) {
        if (conn != null) {                //如果conn连接对象不为空
            try {
                conn.close();            //关闭conn连接对象对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**.
     * 关闭预处理对象
     * @param stmt 预处理对象
     */
    public static void close(final Statement stmt) {
        if (stmt != null) {                //如果pstmt预处理对象不为空
            try {
                stmt.close();            //关闭pstmt预处理对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**.
     * 关闭结果集对象
     * @param rs 结果集对象
     */
    public static void close(final ResultSet rs) {
        if (rs != null) {                //如果rs结果集对象不为null
            try {
                rs.close();                //关闭rs结果集对象
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**.
     * 将游戏保存到数据库
     * @param game 要保存的游戏对象
     */
    public static void saveGame(final Game game) {
        Connection con; //声明一个连接对象
        //遍历查询结果集
        try {
            con = getConnection(); //1.调用方法返回连接
            Statement statement = con.createStatement(); //2.创建statement类对象
            String sqlroom =
                    "REPLACE INTO room(username,roomname,itemname,num) "
                            + "values('%s','%s','%s','%d')";
            //遍历房间
            for (Room room : game.getSaveRooms()) {
                //遍历房间拥有的物品
                for (Item items : room.getItems().keySet()) {
                    //房间信息写入数据库
                    if (statement.executeUpdate(String.
                            format(sqlroom, game.getUser().getName(),
                                    room.getName(), items.getName(),
                                    room.getItems().get(items))) == 0) {
                        System.out.println("Save failure");
                        close(statement);
                        close(con);
                        return;
                    }
                }
            }
            String sqluser =
                    "REPLACE INTO user(username,strength,power,roomname)"
                    + " values('%s','%d','%d','%s')";
            //将用户信息写入数据库
            if (statement.executeUpdate(String.format(
                    sqluser, game.getUser().getName(),
                    game.getUser().getStrength(), game.getUser().getPower(),
                    game.getUser().getCurrentRoom().getName())) == 0) {
                System.out.println("Save failure");
                close(statement);
                close(con);
                return;
            }
            //用户拥有的物品写入数据库
            String sqlItem = "REPLACE INTO item(username,itemname,num)"
                    + " values('%s','%s','%d')";
            for (Item items : game.getUser().getItems().keySet()) {
                if (statement.executeUpdate(String.format(
                        sqlItem, game.getUser().getName(), items.getName(),
                        game.getUser().getItems().get(items))) == 0) {
                    System.out.println("Save failure");
                    close(statement);
                    close(con);
                    return;
                }
            }

            System.out.println("save successfully");

            close(statement);
            close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**.
     * 将user得分存入数据库
     * @param user 通关后的用户
     */
    public static void saveScore(final User user) {
        Connection con; //声明一个连接对象
        //遍历查询结果集
        try {
            con = getConnection(); //1.调用方法返回连接
            Statement statement = con.createStatement(); //2.创建statement类对象
            String sqlscore =
                    "INSERT INTO score(username,num) values('%s','%d')";
            if (statement.executeUpdate(String.
                    format(sqlscore, user.getName(), user.sumPrice())) == 0) {
                System.out.println("Save score failure");
                close(statement);
                close(con);
                return;
            }

            System.out.println("save score successfully");

            close(statement);
            close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**.
     * 查看数据库中是否有改名字,如果有,则命名重复
     * @param name 要查询名字
     * @return 查询结果, 如果有返回false, 否则返回true
     */
    public static boolean compareName(final String name) {
        Connection con; //声明一个连接对象
        //遍历查询结果集
        try {
            con = getConnection(); //1.调用方法返回连接
            Statement statement = con.createStatement(); //2.创建statement类对象
            String sqlUser =
                    "SELECT * FROM user WHERE username = '%s'"; //要执行的SQL语句
            ResultSet user = statement.executeQuery(
                    String.format(sqlUser, name));
            //user表中如果有该名字
            if (user.isAfterLast() != user.isBeforeFirst()) {
                return false;
            }
            close(user);
            String sqlScore =
                    "SELECT * FROM score WHERE username = '%s'"; //要执行的SQL语句
            ResultSet score = statement.executeQuery(
                    String.format(sqlScore, name));
            //score表中如果有该名字
            if (score.isAfterLast() != score.isBeforeFirst()) {
                return false;
            }
            close(user);
            close(statement);
            close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**.
     * 恢复指定用户游玩的游戏
     * @param name 用户
     * @return 恢复的游戏对象
     */
    public static Game getGame(final String name) {
        Game game = new Game(name);
        Connection con; //声明一个连接对象
        //遍历查询结果集
        try {
            con = getConnection(); //1.调用方法返回连接
            Statement statement = con.createStatement(); //2.创建statement类对象
            String sqlUser = "SELECT * FROM user WHERE username = '%s'";
            String sqlRoom = "SELECT * FROM room WHERE username = '%s'";
            String sqlItem = "SELECT * FROM item WHERE username = '%s'";

            //恢复用户信息
            ResultSet user = statement.executeQuery(
                    String.format(sqlUser, name));
            if (user.isAfterLast() == user.isBeforeFirst()) {
                System.out.println("not have this name\n");
                return null;
            }
            while (user.next()) {
                game.getUser().setStrength(user.getInt("strength"));
                game.getUser().setPower(user.getInt("power"));
                Room rooms = game.getRoom(user.getString("roomname"));
                game.getUser().clearPath();
                game.getUser().setPath(rooms);
            }
            close(user);

            //恢复房间信息
            ResultSet room = statement.executeQuery(
                    String.format(sqlRoom, name));
            while (room.next()) {
                Item items = game.getItem(room.getString("itemname"));
                int num = room.getInt("num");
                game.getRoom(room.getString("roomname")).setItem(items, num);
            }
            close(room);

            //恢复用户拥有物品信息
            ResultSet item = statement.executeQuery(
                    String.format(sqlItem, name));
            while (item.next()) {
                Item items = game.getItem(item.getString("itemname"));
                int num = item.getInt("num");
                game.getUser().setItems(items, num);
            }
            close(item);
            close(statement);
            close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return game;
    }

    /**.
     * 返回分数
     * @return 用户名和分数的HashMap
     */
    public static HashMap<String, Integer> score() {
        HashMap<String, Integer> scores = new HashMap<>();
        Connection con; //声明一个连接对象
        //遍历查询结果集
        try {
            con = getConnection(); //1.调用方法返回连接
            Statement statement = con.createStatement(); //2.创建statement类对象
            String sql = "SELECT * FROM score"; //要执行的SQL语句
            ResultSet sc = statement.executeQuery(sql);
            //如果没有用户通关
            if (sc.isAfterLast() == sc.isBeforeFirst()) {
                System.out.println("score list is empty\n");
                return null;
            }
            //得到数据库内容
            while (sc.next()) {
                scores.put(sc.getString("username"), sc.getInt("num"));
            }
            close(sc);
            close(statement);
            close(con);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return scores;
    }
}
