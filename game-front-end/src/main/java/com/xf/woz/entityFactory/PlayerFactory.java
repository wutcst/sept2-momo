/**
 * @description : [玩家实体工厂类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-17 13:52]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-17 13:52]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.entityFactory;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

public class PlayerFactory implements EntityFactory {

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return FXGL.entityBuilder(data)
                .build();
    }
}
