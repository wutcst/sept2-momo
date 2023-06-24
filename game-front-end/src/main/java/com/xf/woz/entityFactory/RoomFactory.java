/**
 * @description : [房间实体工厂类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-17 15:04]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 22:56]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.entityFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.Spawns;
import com.xf.woz.entity.RoomEntityType;

import java.util.Map;

public class RoomFactory implements EntityFactory {

    @Spawns("outside")
    public Entity spawnOutside() {
        return FXGL.entityBuilder()
                .view("room/outside.png")
                .build();
    }

    @Spawns("pub")
    public Entity spawnPub() {
        return FXGL.entityBuilder()
                .view("room/pub.png")
                .build();
    }

    @Spawns("theater")
    public Entity spawnTheater() {
        return FXGL.entityBuilder()
                .view("room/theater.png")
                .build();
    }

    @Spawns("lab")
    public Entity spawnLab() {
        return FXGL.entityBuilder()
                .view("room/lab.png")
                .build();
    }

    @Spawns("office")
    public Entity spawnOffice() {
        return FXGL.entityBuilder()
                .view("room/office.png")
                .build();
    }

    public static Map<String, RoomEntityType> roomMap = Map.of(
            "outside", RoomEntityType.OUTSIDE,
            "pub", RoomEntityType.PUB,
            "theater", RoomEntityType.THEATER,
            "lab", RoomEntityType.LAB,
            "office", RoomEntityType.OFFICE,
            "magic", RoomEntityType.MAGIC
    );

    public static Entity createEntity(RoomEntityType type) {
        switch (type) {
            case OUTSIDE -> {
                return FXGL.entityBuilder().view("room/outside.png").build();
            }
            case PUB -> {
                return FXGL.entityBuilder().view("room/pub.png").build();
            }
            case THEATER -> {
                return FXGL.entityBuilder().view("room/theater.png").build();
            }
            case LAB -> {
                return FXGL.entityBuilder().view("room/lab.png").build();
            }
            case OFFICE -> {
                return FXGL.entityBuilder().view("room/office.png").build();
            }
            case MAGIC -> {
                return FXGL.entityBuilder().view("room/magic.png").build();
            }
            default -> {
                return null;
            }
        }
    }
}
