/**
 * @description : [命令的Protobufclass]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-19 23:17]

 * @updateUser : [张忠瑾]
 * @updateTime : [2023-6-23 23:00]

 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.protoBuf;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@Data
@AllArgsConstructor
public class Command {
    private String commandWord;
    private String secondWord;
}
