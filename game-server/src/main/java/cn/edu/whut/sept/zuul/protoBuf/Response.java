/**
 * @description : [通用回复的protobufclass]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-19 23:22]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-19 23:22]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.protoBuf;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@Data
public class Response {
    private String msg;
}
