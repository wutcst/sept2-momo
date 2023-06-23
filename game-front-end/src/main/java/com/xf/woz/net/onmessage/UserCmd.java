/**
 * 玩家操作路由记录接口
 */
package com.xf.woz.net.onmessage;

import com.xf.woz.net.CmdModule;

public interface UserCmd {
    int CMD = CmdModule.USER_ACTION;

    int LOGIN_VERIFY = 0;

    int GO = 1;

    int BACK = 2;

    int HELP = 3;

    int QUIT = 4;

    int LOOK = 5;

    int DROP = 6;

    int ITEMS = 7;

    int TAKE = 8;

    int UPDATE = 9;

}
