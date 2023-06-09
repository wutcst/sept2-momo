package cn.edu.whut.sept.zuul.command;

import cn.edu.whut.sept.zuul.Game;

import java.util.Random;

public class GetCommand implements GameCommand{
    @Override
    public void execute(Game game) {
        System.out.println("执行 get 命令");

        // 模拟获取物品的逻辑
        String item = getRandomItem();

        if (item != null) {
            System.out.println("你获得了物品：" + item);
        } else {
            System.out.println("这里没有可获得的物品。");
        }

        // 可以添加更多的逻辑代码
    }

    private String getRandomItem() {
        // 模拟随机获取物品的逻辑
        String[] items = {"钥匙", "宝剑", "药水", "金币"};
        int randomIndex = new Random().nextInt(items.length);
        return items[randomIndex];
    }
}






