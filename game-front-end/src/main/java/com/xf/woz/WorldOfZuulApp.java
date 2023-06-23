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
 * @author Alexander
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
        //菜单
        settings.setMainMenuEnabled(true);
        try {
            UserWebsocketClient.me().start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
//        settings.setSceneFactory();
    }

    @Override
    protected void onPreInit() {

        //设置背景图
//        FXGL.loopBGM("BGM_677.mp3");
    }

    @Override
    protected void initGame() {
        FXGL.getGameWorld().addEntityFactory(new PlayerFactory());
        getGameWorld().addEntity(Objects.requireNonNull(RoomFactory.createEntity(RoomEntityType.OUTSIDE)));
        playerEntity = spawn("player");
        playerEntity.addComponent(new PlayerView());
//        spawn("outside");


        FXGL.runOnce(() -> {
            List<String> lines = getAssetLoader().loadText("login.txt");
            Cutscene cutscene = new Cutscene(lines);
            getCutsceneService().startCutscene(cutscene, () -> {
                        getDialogService().showInputBox("请输入用户名",
                                username -> {
                                    getDialogService().showInputBox("请输入密码来登录游戏(用户名不存在则自动注册)",
                                            password -> {
                                                this.player = new Player();
                                                this.player.setName(username);
                                                this.player.setPwd(password);
                                                playerView = playerEntity.getComponent(PlayerView.class);
                                                playerView.login(player);
                                            });
                                });
                    }
            );
        }, Duration.ONE);


    }

    @Override
    protected void initUI() {
        String[] ui = {"help", "go", "quit", "look", "back", "take", "drop", "items"};
        Image image = new Image("assets/textures/ui/button.png");

        Rectangle r1 = new Rectangle(180, 64);
        r1.setFill(new ImagePattern(image));
        Text t1 = FXGL.getUIFactoryService().newText(ui[0]);
        StackPane help = new StackPane(r1, t1);
        List<String> helpTalks = List.of("You are lost. You are alone.",
                "You wander around at the university.",
                "Your command words are left.");
        List<Talk> talks1 = TalkFactory.buildTalkList(helpTalks);
        help.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> TalkScene.getInstance().show(talks1)));
        addUINode(help, 0, 30);


        Rectangle r2 = new Rectangle(180, 64);
        r2.setFill(new ImagePattern(image));
        Text t2 = FXGL.getUIFactoryService().newText(ui[1]);
        StackPane go = new StackPane(r2, t2);

        go.setOnMouseClicked(mouseEvent -> Platform.runLater(() -> {
            List<String> place = FXGLUtils.nowPlayer.getPlace();
            nowPlace = place.subList(place.size() - 1, place.size()).get(0);

            List<String> exits = geto(nowPlace);
            List<String> goTalks = List.of("Go where?",
                    "Exits:" + exits);
            List<Talk> talks2 = TalkFactory.buildTalkList(goTalks);
            TalkScene.getInstance().show(talks2);

            List<StackPane> dirNodes = new ArrayList<>();

            for (int i = 0; i < exits.size(); i++) {
                Rectangle rect = new Rectangle(180, 64);
                rect.setFill(new ImagePattern(image));
                Text text = FXGL.getUIFactoryService().newText(exits.get(i));
                StackPane stackPane = new StackPane(rect, text);
                int finalI = i;
                stackPane.setOnMouseClicked(mouseEventDir -> {
                    dirNodes.forEach(FXGL::removeUINode);
                    try {
                        String s = FXGLUtils.newRoom(nowPlace, exits.get(finalI));
                        FXGLUtils.updateRoom(s);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

                dirNodes.add(stackPane);
                addUINode(stackPane, 200, 80 * (i + 1) + 30);
            }

        }));
        addUINode(go, 0, 80 + 30);

        Rectangle r3 = new Rectangle(180, 64);
        r3.setFill(new ImagePattern(image));
        Text t3 = FXGL.getUIFactoryService().newText(ui[2]);
        StackPane quit = new StackPane(r3, t3);
        quit.setOnMouseClicked(mouseEventQuit -> Platform.runLater(() -> {
            List<String> quitStrings = List.of("Thank you for playing.  Good bye.",
                    "We will save the data for you.");
            List<Talk> talks3 = TalkFactory.buildTalkList(quitStrings);
            TalkScene.getInstance().show(talks3);
            FXGLUtils.updateAllRoom();
            FXGLUtils.updatePlayer();
        }));
        addUINode(quit, 0, 80 * 2 + 30);

        Rectangle r4 = new Rectangle(180, 64);
        r4.setFill(new ImagePattern(image));
        Text t4 = FXGL.getUIFactoryService().newText(ui[3]);
        StackPane look = new StackPane(r4, t4);
        look.setOnMouseClicked(mouseEvent -> {
            List<String> place = FXGLUtils.nowPlayer.getPlace();
            nowPlace = place.subList(place.size() - 1, place.size()).get(0);
            List<String> lookTalks = List.of("You are in the " + nowPlace + ".",
                    "You can see:" + FXGLUtils.roomItems.get(nowPlace));
            List<Talk> talks4 = TalkFactory.buildTalkList(lookTalks);
            TalkScene.getInstance().show(talks4);
        });
        addUINode(look, 0, 80 * 3 + 30);

        Rectangle r5 = new Rectangle(180, 64);
        r5.setFill(new ImagePattern(image));
        Text t5 = FXGL.getUIFactoryService().newText(ui[4]);
        StackPane back = new StackPane(r5, t5);
        back.setOnMouseClicked(mouseEventBack -> {
            FXGLUtils.backRoom();
        });
        addUINode(back, 0, 80 * 4 + 30);

        Rectangle r6 = new Rectangle(180, 64);
        r6.setFill(new ImagePattern(image));
        Text t6 = FXGL.getUIFactoryService().newText(ui[5]);
        StackPane take = new StackPane(r6, t6);
        take.setOnMouseClicked(mouseEvent -> {
            List<String> place = FXGLUtils.nowPlayer.getPlace();
            nowPlace = place.subList(place.size() - 1, place.size()).get(0);
            List<String> takeTalks = List.of("Take what?",
                    "Items:" + FXGLUtils.roomItems.get(nowPlace));
            List<Talk> talks6 = TalkFactory.buildTalkList(takeTalks);
            TalkScene.getInstance().show(talks6);

            List<StackPane> itemNodes = new ArrayList<>();

            Map<String, Integer> itemCount = FXGLUtils.roomItems.get(nowPlace);
            ArrayList<String> itemsName = new ArrayList<>(itemCount.keySet());
            int uiPlace = 5;

            for (int i = 0; i < FXGLUtils.roomItems.get(nowPlace).size(); i++) {
                String s = itemsName.get(i);
                if (itemCount.get(s) == 0) {
                    continue;
                }
                Rectangle rect = new Rectangle(180, 64);
                rect.setFill(new ImagePattern(image));
                Text text = FXGL.getUIFactoryService().newText(s);
                StackPane stackPane = new StackPane(rect, text);
                stackPane.setOnMouseClicked(mouseEventDir -> {
                    itemNodes.forEach(FXGL::removeUINode);
                    try {
                        FXGLUtils.takeItem(nowPlace, s);
                        FXGL.getNotificationService().pushNotification("You take " + s);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

                itemNodes.add(stackPane);
                addUINode(stackPane, 200, 80 * uiPlace + 30);
                uiPlace++;
            }
        });
        addUINode(take, 0, 80 * 5 + 30);

        Rectangle r7 = new Rectangle(180, 64);
        r7.setFill(new ImagePattern(image));
        Text t7 = FXGL.getUIFactoryService().newText(ui[6]);
        StackPane drop = new StackPane(r7, t7);
        drop.setOnMouseClicked(mouseEvent -> {
            List<String> place = FXGLUtils.nowPlayer.getPlace();
            nowPlace = place.subList(place.size() - 1, place.size()).get(0);
            List<String> dropTalks = List.of("Drop what?",
                    "Items:" + FXGLUtils.nowPlayer.getItems());
            List<Talk> talks7 = TalkFactory.buildTalkList(dropTalks);
            TalkScene.getInstance().show(talks7);

            List<StackPane> itemNodes = new ArrayList<>();

            List<String> itemList = FXGLUtils.nowPlayer.getItems();
            //统计itemList中各元素出现次数
            Map<String, Integer> itemCount = new HashMap<>();
            for (String item : itemList) {
                Integer count = itemCount.get(item);
                itemCount.put(item, (count == null) ? 1 : count + 1);
            }
            ArrayList<String> itemsName = new ArrayList<>(itemCount.keySet());
            int uiPlace = 6;

            if (itemsName.size() != 0) {
                Rectangle rect = new Rectangle(180, 64);
                rect.setFill(new ImagePattern(image));
                Text text = FXGL.getUIFactoryService().newText("all");
                StackPane stackPane = new StackPane(rect, text);
                stackPane.setOnMouseClicked(mouseEventDir -> {
                    itemNodes.forEach(FXGL::removeUINode);
                    try {
                        FXGLUtils.dropAllItem(nowPlace);
                        FXGL.getNotificationService().pushNotification("You drop all");
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });
                itemNodes.add(stackPane);
                addUINode(stackPane, 200, 80 * uiPlace + 30);
                uiPlace++;
            }
            for (int i = 0; i < itemCount.size(); i++) {
                String s = itemsName.get(i);
                if (itemCount.get(s) == 0) {
                    continue;
                }
                Rectangle r = new Rectangle(180, 64);
                r.setFill(new ImagePattern(image));
                Text t = FXGL.getUIFactoryService().newText(s);
                StackPane stackPane1 = new StackPane(r, t);
                stackPane1.setOnMouseClicked(mouseEventDir -> {
                    itemNodes.forEach(FXGL::removeUINode);
                    try {
                        FXGLUtils.dropItem(nowPlace, s);
                        FXGL.getNotificationService().pushNotification("You drop " + s);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                });

                itemNodes.add(stackPane1);
                addUINode(stackPane1, 200, 80 * uiPlace + 30);
                uiPlace++;
            }

        });
        addUINode(drop, 0, 80 * 6 + 30);

        Rectangle r8 = new Rectangle(180, 64);
        r8.setFill(new ImagePattern(image));
        Text t8 = FXGL.getUIFactoryService().newText(ui[7]);
        StackPane items = new StackPane(r8, t8);
        items.setOnMouseClicked(mouseEvent -> {
            List<String> place = FXGLUtils.nowPlayer.getPlace();
            nowPlace = place.subList(place.size() - 1, place.size()).get(0);
            List<String> itemsTalks = new ArrayList<>(List.of("You have:" + FXGLUtils.nowPlayer.getItems(),
                    "You can see:" + FXGLUtils.roomItems.get(nowPlace)));
            if (FXGLUtils.nowPlayer.getItems().contains("magic cookie")) {
                itemsTalks.add("You can use magic cookie to increase load.");

                Rectangle rect = new Rectangle(180, 64);
                rect.setFill(new ImagePattern(image));
                Text text = FXGL.getUIFactoryService().newText("use magic cookie");
                StackPane stackPane = new StackPane(rect, text);
                stackPane.setOnMouseClicked(mouseEventDir -> {
                    removeUINode(stackPane);
                    FXGLUtils.useMagicCookie();
                });
                addUINode(stackPane, 200, 80 * 7 + 30);
            }
            List<Talk> talks5 = TalkFactory.buildTalkList(itemsTalks);
            TalkScene.getInstance().show(talks5);
        });
        addUINode(items, 0, 80 * 7 + 30);


    }

    @Override
    protected void onUpdate(double tpf) {
        super.onUpdate(tpf);
//        if (player != null) {
//            player = FXGLUtils.nowPlayer;
//            List<String> track = player.getPlace();
//            List<String> lastPlace = track.subList(track.size() - 1, track.size());
//            nowPlace = lastPlace.get(0);
//        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
