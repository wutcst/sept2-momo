package cn.edu.whut.sept.zuul;

import cn.edu.whut.sept.zuul.manner.*;
import cn.edu.whut.sept.zuul.room.*;

import java.util.Vector;

/**.
 * 该类是“World-of-Zuul”应用程序的主类。
 * <p>
 * <p>
 * Game类的实例将创建并初始化所有其他类:它创建所有房间，并将它们连接成迷宫；它创建解析器
 * 接收用户输入，并将用户输入转换成命令后开始运行游戏。
 * 属性  user:游戏中的玩家,manners:游戏中拥有的动作,saveRooms:游戏中拥有的房间,items:游戏中拥有的物品
 *
 * @author Michael Kölling and David J. Barnes
 * @version 1.0
 */
public class Game {
    private Parser parser;
    private User user;
    private Vector<Manner> manners;
    private Vector<Room> saveRooms;
    private Vector<Item> items;

    /**
     * @param name 玩家名
     */
    public Game(final String name) {
        createItems();
        createUser(createRooms(), name);
        createManner();
        parser = new Parser();
    }

    /**.
     * 初始化物品信息
     */
    private void createItems() {
        items = new Vector<>();
        items.add(new Item("book", ConstFile.BOOKPRICE,
                ConstFile.BOOKWEIGTH));
        items.add(new Item("chair", ConstFile.CHAIRPRICE,
                ConstFile.CHAIRWEIGTH));
        items.add(new Item("flower", ConstFile.FLOWERPRICE,
                ConstFile.FLOWERWEIGTH));
        items.add(new Item("cookie", ConstFile.COOKIEPRICE,
                ConstFile.COOKIEWEIGTH));
    }

    /**.
     * 初始化用户
     *
     * @param room 用户初始房间
     * @param name 用户名
     */
    private void createUser(final Room room, final String name) {
        user = new User(name,  room);
    }

    /**.
     * 初始化拥有的动作
     */
    private void createManner() {
        manners = new Vector<>();
        manners.add(new MannerGo(CommandWord.GO, user));
        manners.add(new MannerHelp(CommandWord.HELP));
        manners.add(new MannerQuit(CommandWord.QUIT));
        manners.add(new MannerEat(CommandWord.EAT, user));
        manners.add(new MannerDrop(CommandWord.DROP, user));
        manners.add(new MannerTake(CommandWord.TAKE, user));
        manners.add(new MannerBack(CommandWord.BACK, user));
        manners.add(new MannerDeal(CommandWord.DEAL, user));
        manners.add(new MannerView(CommandWord.VIEW, user));
        manners.add(new MannerSave(CommandWord.SAVE, this));
    }

    /**.
     * 创建所有房间对象并连接其出口用以构建迷宫,并给房间添加物品
     *
     * @return 用户初始的房间
     */
    private Room createRooms() {
        //Room door, exchange, dining, study, garden, portal, bedroom;

        // create the rooms
        Room door = new CommonRoom("outside the main entrance of your room",
                "door");
        Room exchange = new DealRoom("Change everything you have into money",
                "exchange");
        Room dining = new CommonRoom("It is a place have something to eat",
                "dining");
        Room bedroom = new RestRoom("You can sleep in the room",
                "bedroom");
        Room study = new CommonRoom("a room that have books",
                "study");
        Room garden = new ConsumeRoom("You can exercise in this place",
                "garden");
        saveRooms = new Vector<>();
        saveRooms.add(dining);
        saveRooms.add(door);
        saveRooms.add(bedroom);
        saveRooms.add(study);
        saveRooms.add(garden);
        saveRooms.add(exchange);
        Room portal = new PortalRoom("You will be teleported",
                saveRooms, "portal");
        saveRooms.add(portal);
        // initialise room exits
        door.setExit("west", study);
        door.setExit("north", exchange);
        door.setExit("south", garden);
        door.setExit("east", dining);

        study.setExit("east", door);
        study.setExit("north", bedroom);

        bedroom.setExit("south", study);

        exchange.setExit("south", door);

        garden.setExit("north", door);
        garden.setExit("south", portal);

        portal.setExit("north", garden);

        dining.setExit("west", door);

        //给房间添加物品
        study.setItem(getItem("book"), ConstFile.STUDYBOOK);
        study.setItem(getItem("chair"), ConstFile.STUDYCHAIR);
        bedroom.setItem(getItem("chair"), ConstFile.BEDROOMCHAIR);
        bedroom.setItem(getItem("flower"), ConstFile.BEDROOMFLOWER);
        bedroom.setItem(getItem("book"), ConstFile.BEDROOMBOOK);
        dining.setItem(getItem("chair"), ConstFile.DININGCHAIR);
        dining.setItem(getItem("cookie"), ConstFile.DININGCOOKIE);
        garden.setItem(getItem("flower"), ConstFile.GARDENFLOWER);

        return door;
    }

    /**
     * @param name 物品名
     * @return 物品名对应的Item对象
     */
    public Item getItem(final String name) {
        for (Item item : items) {
            if (item.getName().equals(name)) {
                return item;
            }
        }
        return null;
    }

    /**
     * @return 全部房间
     */
    public Vector<Room> getSaveRooms() {
        return saveRooms;
    }

    /**
     * @return 用户
     */
    public User getUser() {
        return user;
    }

    /**
     * @param aUser 用户
     */
    public void setUser(final User aUser) {
        this.user = aUser;
    }

    /**
     * @param name 房间名
     * @return 房间名对应的房间对象
     */
    public Room getRoom(final String name) {
        for (Room room : saveRooms) {
            if (room.getName().equals(name)) {
                return room;
            }
        }
        return null;
    }

    /**
     * 游戏主控循环，直到用户输入退出命令后结束整个程序.
     */
    public void play() {
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.

        boolean finished = false;
        while (!finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * 向用户输出欢迎信息.
     */
    private void printWelcome() {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(user.getCurrentRoom().getLongDescription());
    }

    /**
     * 执行用户输入的游戏指令.
     *
     * @param command 待处理的游戏指令，由解析器从用户输入内容生成.
     * @return 如果执行的是游戏结束指令，则返回true，否则返回false.
     */
    public boolean processCommand(final Command command) {
        boolean wantToQuit = false;

        if (command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        CommandWord commandWord = command.getCommandWord();
        //遍历命令,查看哪个命令与输入相匹配
        for (Manner manner : manners) {
            if (manner.compareCommand(commandWord)) {
                wantToQuit = manner.manageCommand(command);
                break;
            }
        }
        return wantToQuit;
    }

}
