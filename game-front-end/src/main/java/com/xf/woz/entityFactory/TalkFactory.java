/**
 * @description : [对话生成工厂类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-19 21:42]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-19 21:42]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.entityFactory;

import com.xf.woz.pojo.Talk;

import java.util.ArrayList;
import java.util.List;

public class TalkFactory {
    public static List<Talk> buildTalkList(List<String> content) {
        List<Talk> talkList = new ArrayList<>();
        content.forEach(s -> talkList.add(new Talk(s, "Server", "assets/textures/server.png")));
        return talkList;
    }
}
