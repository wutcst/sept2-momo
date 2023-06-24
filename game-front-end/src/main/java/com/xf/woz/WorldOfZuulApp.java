/*
游戏启动类
*/
package com.xf.woz;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.cutscene.Cutscene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.localization.Language;
import com.xf.woz.components.PlayerView;
import com.xf.woz.entity.RoomEntityType;
import com.xf.woz.entityFactory.PlayerFactory;
import com.xf.woz.entityFactory.RoomFactory;
import com.xf.woz.entityFactory.TalkFactory;
import com.xf.woz.net.UserWebsocketClient;
import com.xf.woz.pojo.Player;
import com.xf.woz.pojo.Talk;
import com.xf.woz.scene.TalkScene;
import com.xf.woz.util.FXGLUtils;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.almasb.fxgl.dsl.FXGLForKtKt.getCutsceneService;

/**
 * 游戏启动类
 */
@Slf4j
public class WorldOfZuulApp extends GameApplication {

    private Entity playerEntity = null;
    private Player player = null;
    private PlayerView playerView;
    private String nowPlace = "outside";

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("outside", List.of("south", "west", "east"));
        vars.put("magic", List.of("north", "east"));
        vars.put("pub", List.of("south", "east"));
        vars.put("lab", List.of("north", "west", "east"));
        vars.put("office", List.of("west"));
        vars.put("theater", List.of("west"));
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setTitle("World of Zuul");
        settings.setHeight(1080);
        settings.setWidth(1920);
        settings.setVersion("0.1");
        settings.setAppIcon("icon.png");
        settings.getSupportedLanguages().add(Language.CHINESE);
        settings.setDefaultLanguage(Language.CHINESE);
        settings.setMainMenuEnabled(true);

        try {
            UserWebsocketClient.me().start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    protected void onPreInit() {
        // 设置背景音乐
        FXGL.loopBGM("BGM_677.mp3");
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new PlayerFactory());
        getGameWorld().addEntity(Objects.requireNonNull(RoomFactory.createEntity(RoomEntityType.OUTSIDE)));
        playerEntity = spawn("player");
        playerEntity.addComponent(new PlayerView());

        FXGL.runOnce(() -> {
            List<String> lines = getAssetLoader().loadText("login.txt");
            Cutscene cutscene = new Cutscene(lines);
            getCutsceneService().startCutscene(cutscene, () -> {
                getDialogService().showInputBox("请输入用户名", username -> {
                    getDialogService().showInputBox("请输入密码来登录游戏", password -> {
                        UserWebsocketClient.me().login(username, password, result -> {
                            Platform.runLater(() -> {
                                if (result.isSuccess()) {
                                    player = result.getData();
                                    if (player.getHeadImage() != null) {
                                        Image headImage = new Image(player.getHeadImage());
                                        Rectangle clip = new Rectangle(96, 96);
                                        clip.setArcWidth(10);
                                        clip.setArcHeight(10);
                                        headImageProperty().setValue(new ImagePattern(headImage));
                                    }
                                    showProfile(player);
                                } else {
                                    getDialogService().showMessageBox("登录失败：" + result.getMessage());
                                }
                            });
                        });
                    });
                });
            });
        }, Duration.seconds(1));
    }

    private void showProfile(Player player) {
        StackPane profilePane = new StackPane();
        profilePane.setPrefSize(300, 400);
        profilePane.getStyleClass().add("profile-pane");

        Text nameLabel = new Text(player.getName());
        nameLabel.getStyleClass().add("profile-name");

        profilePane.getChildren().add(nameLabel);
        getGameScene().addUINode(profilePane, 100, 100);
    }

    @Override
    protected void initUI() {
        // 设置 UI 样式
        FXGLUtils.loadCSS("style.css");
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player != null) {
            player.setPosX(playerEntity.getX());
            player.setPosY(playerEntity.getY());
            UserWebsocketClient.me().updatePlayer(player, null);
        }
    }

    @Override
    protected void initPhysics() {
        // 这里添加物理碰撞的逻辑
    }

    @Override
    protected void initInput() {
        // 这里添加键盘输入的逻辑
    }

    public static void main(String[] args) {
        launch(args);
    }
}
