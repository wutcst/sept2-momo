/**
 * @description : [talk类实体，用于加载对话]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-19 21:41]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-19 21:41]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Talk {
    //讨论的文字
    private String text;
    //说话者
    private String addressor;
    //说话者图片地址
    private String addressorImg;

}
