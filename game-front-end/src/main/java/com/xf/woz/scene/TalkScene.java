/*
fxgl的对话框类
 */
package com.xf.woz.scene;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.ui.FontType;
import com.xf.woz.pojo.Talk;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class TalkScene extends SubScene {
    //对话的列表
    private final List<Talk> list = new ArrayList<>();
    //text的文本
    private final Text text;
    //矩形
    private final Rectangle rectangle;
    //存放text和包裹的矩形的容器
    private StackPane stackPane;
    //左边的立绘
    private final ImageView speakerImg;

    private static TalkScene instance = new TalkScene();

    //单例模式，方便调用
    public static synchronized TalkScene getInstance() {
        if (instance == null) {
            instance = new TalkScene();
        }
        return instance;
    }

    private TalkScene() {
        double width = FXGL.getAppWidth();
        double height = FXGL.getAppHeight() / 4d;
        //初始化UI框体图片
        Image image = new Image("assets/textures/ui/talkScene.png");
        ImageView imageView = new ImageView(image);
        //保持长宽比
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setTranslateX(0);
        imageView.setTranslateY(height * 3 + 30);

        //初始化立绘的位置
        speakerImg = new ImageView();
        speakerImg.setPreserveRatio(true);
        speakerImg.setFitWidth(width / 3d);
        speakerImg.setTranslateX(20);
        speakerImg.setTranslateY(height * 2.5);

        //初始化文本框
        text = FXGL.getUIFactoryService().newText("", Color.PINK, FontType.GAME, 22);
        text.setVisible(true);
        text.setFill(new LinearGradient(0, 0, 1, 2, true, CycleMethod.REPEAT, new Stop(0, Color.AQUA), new Stop(0.5f, Color.RED)));
        text.setStrokeWidth(1);
        text.setStroke(Color.PINK);
        text.setTranslateX(20);
        text.setTranslateY(height * 3 + 20);

        rectangle = new Rectangle(width, height);
        rectangle.setTranslateX(0);
        rectangle.setTranslateY(height * 3);

        //将矩形透明度调低
        rectangle.setOpacity(0);
        getContentRoot().getChildren().add(imageView);
        //添加一个输入事件，当鼠标左键点击时进行下一条对话
        getInput().addAction(new UserAction("remove") {
            @Override
            protected void onActionBegin() {
                getContentRoot().getChildren().remove(stackPane);
                getContentRoot().getChildren().remove(speakerImg);
                nextTalk();
            }
        }, MouseButton.PRIMARY);
    }

    //展示对话框的方法
    public synchronized void show(List<Talk> talkList) {
        FXGL.getSceneService().pushSubScene(this);
        //对话内容拆分成多条，每条最多显示100个字符
        for (Talk talk : talkList) {
            String message = talk.getText();
            int length = message.length();
            if (length > 100) {
                for (int i = 0; i < length / 100; i++) {
                    String substring = message.substring(0, Math.min(message.length(), 100));
                    Talk subTalk = new Talk(substring, talk.getAddressorImg());
                    list.add(subTalk);
                    message = message.substring(100);
                }
            }
            if (!message.isEmpty()) {
                Talk remainingTalk = new Talk(message, talk.getAddressorImg());
                list.add(remainingTalk);
            }
        }
        nextTalk();
    }

    public void nextTalk() {
        if (list.isEmpty()) {
            getInput().clearAll();
            FXGL.getSceneService().popSubScene();
        } else {
            Talk talk = list.remove(0);
            showTalk(talk);
        }
    }

    /**
     * 展示对话内容
     */
    private void showTalk(Talk talk) {
        text.setText(talk.getText());
        StackPane stackPane = new StackPane(rectangle, text);
        this.stackPane = stackPane;

        speakerImg.setImage(new Image(talk.getAddressorImg()));
        getContentRoot().getChildren().add(speakerImg);
        getContentRoot().getChildren().add(stackPane);
    }
}
