/**
 * @description : [Item实体]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-19 20:10]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-19 20:10]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    private String name;
    private String description;
    private int weight;
}

