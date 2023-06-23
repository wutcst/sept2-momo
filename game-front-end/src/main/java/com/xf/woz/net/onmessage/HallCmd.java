/**
 * Hall路由接口
 */
package com.xf.woz.net.onmessage;

import com.xf.woz.net.CmdModule;

public interface HallCmd {
    int CMD = CmdModule.HALL_ACTION;

    int CONNECT_VERIFY = 0;

    int ROOM_ITEMS = 1;

    int ROOM_UPDATE = 2;
}
