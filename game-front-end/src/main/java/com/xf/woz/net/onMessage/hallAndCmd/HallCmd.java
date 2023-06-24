/**
 * Hall路由接口
 */
package com.xf.woz.net.onMessage.hallAndCmd;

public interface HallCmd {
    int CMD = CmdModule.HALL_ACTION;

    int CONNECT_VERIFY = 0;

    int ROOM_ITEMS = 1;

    int ROOM_UPDATE = 2;
}
