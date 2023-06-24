/**
 * @description : [UserAction的测试类]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-26 15:33]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-26 15:33]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.Action;

import cn.edu.whut.sept.zuul.action.UserAction;
import cn.edu.whut.sept.zuul.entity.Player;
import cn.edu.whut.sept.zuul.protoBuf.RoomProtoBuf;
import org.junit.Test;

import java.util.Objectublic class UserActionTest {
    @Test
    public void updatePlayerTest() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.updatePlayer(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());

    }

    @Test
    public void playerLogintest1() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.login(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());
    }ublic class UserActionTest {
    @Test
    public void updatePlayerTest() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.updatePlayer(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());

    }

    @Test
    public void playerLogintest1() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.login(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());
    }ublic class UserActionTest {
    @Test
    public void updatePlayerTest() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.updatePlayer(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());

    }

    @Test
    public void playerLogintest1() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.login(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());
    }ublic class UserActionTest {
    @Test
    public void updatePlayerTest() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.updatePlayer(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());

    }

    @Test
    public void playerLogintest1() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.login(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());
    }ublic class UserActionTest {
    @Test
    public void updatePlayerTest() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.updatePlayer(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());

    }

    @Test
    public void playerLogintest1() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.login(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());
    }ublic class UserActionTest {
    @Test
    public void updatePlayerTest() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.updatePlayer(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());

    }

    @Test
    public void playerLogintest1() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.login(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());
    }ublic class UserActionTest {
    @Test
    public void updatePlayerTest() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.updatePlayer(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());

    }

    @Test
    public void playerLogintest1() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.login(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());
    }s;

public class UserActionTest {
    @Test
    public void updatePlayerTest() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.updatePlayer(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());

    }

    @Test
    public void playerLogintest1() {
        UserAction userAction = new UserAction();
        Player player = new Player();
        player.setPwd("123456");
        player.setName("jack");
        Player player1 = userAction.login(player, null);
        assert Objects.equals(player1.getName(), player.getName());
        assert Objects.equals(player1.getItems(), player.getItems());
    }


    @Test
    public void playerLogintest() {
        UserAction userAction = new UserAction();
        RoomProtoBuf roomProtoBuf = new RoomProtoBuf();
        roomProtoBuf.setName("pub");
        RoomProtoBuf roomItem = userAction.getRoomItem(roomProtoBuf, null);
        assert roomItem.getItems().get(0).equals("magic cookie");

    }
}
