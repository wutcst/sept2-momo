/**
 * @description : [玩家实体]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-19 20:08]
 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 23:00]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.entity;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.Data;

import java.util.List;

@Data
@ProtobufClass
public class Player {
    private String name;
    private String pwd;
    private List<String> place;
    private List<String> items;
    private int packWeight;
}
