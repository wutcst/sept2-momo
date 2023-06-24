package com.xf.woz.util;

import com.xf.woz.pojo.Room;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RoomGenerator {
    public static Room generateRandomRoom() {
        Random random = new Random();

        String[] names = {"theater", "pub", "lab", "office","magic"};
        String[] descriptions = {"in a lecture theater", "in the campus pub", "in a computing lab","in the computing admin office","teleport you to a random room"};

        int randomIndex = random.nextInt(names.length);
        String randomName = names[randomIndex];
        String randomDescription = descriptions[randomIndex];

        Map<String, Room> exits = new HashMap<>();
        String[] directions = {"east", "south", "west", "north"};
        for (String direction : directions) {
            if (random.nextBoolean()) {
                Room exitRoom = generateRandomRoom();
                exits.put(direction, exitRoom);
            }
        }

        Room randomRoom = new Room(randomName,randomDescription);
        randomRoom.setExits(exits);

        return randomRoom;
    }
}
