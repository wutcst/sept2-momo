/**
 * @description : [实现游戏各种功能]
 * @author : [肖峰]
 * @version : [v1.1]
 * @createTime : [2022-12-23 20:36]
 * @updateUser : [肖峰]
 * @updateTime : [2023-06-24 10:00]
 * @updateRemark : [修改了部分方法实现]
 */
package com.xf.woz.util;

import cn.hutool.core.util.StrUtil;
import com.almasb.fxgl.dsl.FXGL;
import com.xf.woz.entity.RoomEntityType;
import com.xf.woz.entityFactory.RoomFactory;
import com.xf.woz.entityFactory.TalkFactory;
import com.xf.woz.net.onmessage.LookOnMessage;
import com.xf.woz.net.onmessage.UpdateAllRoomOnMessage;
import com.xf.woz.net.onmessage.UpdatePlayerOnMessage;
import com.xf.woz.pojo.Item;
import com.xf.woz.pojo.Player;
import com.xf.woz.pojo.Room;
import com.xf.woz.pojo.Talk;
import com.xf.woz.protoBuf.RoomProtoBuf;
import com.xf.woz.scene.TalkScene;
import javafx.application.Platform;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.*;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.runOnce;

@Slf4j
public class FXGLUtils {
    public static Player nowPlayer = null;
    public static Room outside, theater, pub, lab, office, magic;
    public static Item apple, bigApple, alcoholicChocolate, magicCookie;
    public static Map<String, Map<String, Integer>> roomItems = new HashMap<>();

    static {

        // create the rooms
        outside = new Room("outside", "outside the main entrance of the university");
        theater = new Room("theater", "in a lecture theater");
        pub = new Room("pub", "in the campus pub");
        lab = new Room("lab", "in a computing lab");
        office = new Room("office", "in the computing admin office");
        magic = new Room("magic", "teleport you to a random room");

        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);

        theater.setExit("west", outside);

        pub.setExit("east", outside);
        pub.setExit("south", magic);

        lab.setExit("north", outside);
        lab.setExit("east", office);
        lab.setExit("west", magic);

        office.setExit("west", lab);

        magic.setExit("north", pub);
        magic.setExit("east", lab);

        apple = new Item("apple", "Restore 50 degree of satiety after eating, and increase 5 maximum degree of satiety when satiety is full.", 1);
        bigApple = new Item("big apple", "Restore 100 degree of satiety after eating, and increase 10 maximum degree of satiety when satiety is full.", 2);
        alcoholicChocolate = new Item("alcoholic chocolate", "Restores 20 satiety after eating, and increases experience by 800 if eaten by Adventure members.", 1);
        magicCookie = new Item("magic cookie", "Increases the player's load capacity.", 1);

        LookOnMessage.me().request(FXGLUtils.outside);
        LookOnMessage.me().request(FXGLUtils.pub);
        LookOnMessage.me().request(FXGLUtils.magic);
        LookOnMessage.me().request(FXGLUtils.office);
        LookOnMessage.me().request(FXGLUtils.theater);
        LookOnMessage.me().request(FXGLUtils.lab);



    }

    public static void updateRoom(String roomName) {
        Platform.runLater(() -> {
            RoomEntityType roomType = RoomFactory.roomMap.get(roomName);
            getGameWorld().addEntity(Objects.requireNonNull(RoomFactory.createEntity(roomType)));
            if (roomType == RoomEntityType.MAGIC) {
                List<String> magicRoomTalks = List.of("This is a magic room.",
                        "You are teleported to a random room");
                List<Talk> talks = TalkFactory.buildTalkList(magicRoomTalks);
                runOnce(() -> {
                    TalkScene.getInstance().show(talks);
                    //从枚举中随机获取一个房间类型
                    Random random = new Random();
                    RoomEntityType newRoomType = RoomEntityType.values()[random.nextInt(RoomEntityType.values().length)];
                    updateRoom(newRoomType.name().toLowerCase(Locale.ROOT));

                }, Duration.millis(1));
            } else {
                if (nowPlayer != null) {
                    Room currentRoom = getCurrentRoom(roomName);
                    if (currentRoom != null) {
                        runOnce(() -> TalkScene.getInstance().show(TalkFactory.buildTalkList(List.of(currentRoom.getLongDescription()))), Duration.millis(1));
                        nowPlayer.getPlace().add(roomName);
                    }
                }
            }
        });
    }

    public static void backRoom() {
        List<String> place = nowPlayer.getPlace();
        if (place.size() == 1) {
            FXGL.getNotificationService().pushNotification("We're back in the original room.");
            return;
        } else {
            place.remove(place.size() - 1);
        }
        updateRoom(place.remove(place.size() - 1));
    }

    public static String newRoom(String name, String dir) throws NoSuchFieldException, IllegalAccessException {
        Field oriRoom = FXGLUtils.class.getDeclaredField(name);
        Room o = (Room) oriRoom.get(null);
        return o.getExit(dir).getName();
    }

    public static void updatePlayer() {
        UpdatePlayerOnMessage.me().request(nowPlayer);
    }

    public static void setItems(String name, List<String> items) {
        Map<String, Integer> itemsCount = new HashMap<>();
        for (String item : items) {
            if (StrUtil.isNotBlank(item)) {
                itemsCount.put(item, itemsCount.getOrDefault(item, 0) + 1);
            }
        }
        roomItems.put(name, itemsCount);
    }

    public static void takeItem(String roomName, String itemName) throws NoSuchFieldException, IllegalAccessException {
        String[] s = itemName.split(" ");
        String temp = itemName;
        if (s.length == 2) {
            temp = s[0] + s[1].substring(0, 1).toUpperCase(Locale.ROOT) + s[1].substring(1);
        }

        Field oriRoom = FXGLUtils.class.getDeclaredField(temp);
        Item o = (Item) oriRoom.get(null);
        if (getLoad() + o.getWeight() > nowPlayer.getPackWeight()) {
            FXGL.getNotificationService().pushNotification("Your backpack is full");
            return;
        }
        Map<String, Integer> itemsCount = roomItems.get(roomName);
        if (itemsCount != null) {
            Integer count = itemsCount.get(itemName);
            if (count != null) {
                itemsCount.put(itemName, count - 1);
            }
        }
        nowPlayer.getItems().add(itemName);
    }

    //获得当前负重
    public static int getLoad() {
        int load = 0;
        for (String item : nowPlayer.getItems()) {
            if ("big apple".equals(item)) {
                load += 2;
            } else {
                load += 1;
            }
        }
        return load;
    }

    public static void dropItem(String roomName, String itemName) throws NoSuchFieldException, IllegalAccessException {
        String[] s = itemName.split(" ");
        String temp = itemName;
        if (s.length == 2) {
            temp = s[0] + s[1].substring(0, 1).toUpperCase(Locale.ROOT) + s[1].substring(1);
        }

        Field oriRoom = FXGLUtils.class.getDeclaredField(temp);
        Item o = (Item) oriRoom.get(null);

        nowPlayer.getItems().remove(itemName);
        Map<String, Integer> itemsCount = roomItems.get(roomName);
        if (itemsCount != null) {
            Integer count = itemsCount.get(itemName);
            if (count != null) {
                itemsCount.put(itemName, count + 1);
            }
        }
    }

    public static void dropAllItem(String roomName) throws NoSuchFieldException, IllegalAccessException {
        List<String> items = nowPlayer.getItems();
        for (String item : items) {
            dropItem(roomName, item);
        }
    }

    public static void updateAllRoom() {
        String[] rooms = new String[]{"outside", "pub", "lab", "office", "theater"};
        for (int i = 0; i < rooms.length; i++) {
            List<String> items = new ArrayList<>();
            Map<String, Integer> itemsCount = roomItems.get(rooms[i]);
            if (itemsCount != null) {
                for (Map.Entry<String, Integer> entry : itemsCount.entrySet()) {
                    String item = entry.getKey();
                    Integer count = entry.getValue();
                    for (int integer = 0; integer < count; integer++) {
                        items.add(item);
                    }
                }
            }
            RoomProtoBuf roomProtoBuf = new RoomProtoBuf(rooms[i], items);
            UpdateAllRoomOnMessage.me().request(roomProtoBuf);
        }
    }

    public static void useMagicCookie() {
        FXGLUtils.nowPlayer.getItems().remove("magic cookie");
        FXGLUtils.nowPlayer.setPackWeight(FXGLUtils.nowPlayer.getPackWeight() + 1);
        FXGL.getNotificationService().pushNotification("You have used a magic cookie, your packweight + 1");
    }
}
