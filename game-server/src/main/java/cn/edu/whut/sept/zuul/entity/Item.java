/**
 * @description : [物品实体]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-19 20:10]

 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 23:00]

 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.entity;

import lombok.Data;

@Data
public class Item {
    private String name;
    private String description;
    private int weight;
}
