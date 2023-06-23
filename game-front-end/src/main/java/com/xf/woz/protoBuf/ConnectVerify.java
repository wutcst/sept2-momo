/**
 * @description : [ConnectVerify，连接验证protobufclass]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-18 23:15]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-18 23:15]
 * @updateRemark : [说明本次修改内容]
 */
package com.xf.woz.protoBuf;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@Data
public class ConnectVerify {
    private String msg;
}
