/**
 * @description : [房间信息的protobufclass]
 * @author : [肖峰]
 * @version : [v1.0]
 * @createTime : [2022-12-25 19:58]
 * @updateUser : [肖峰]
 * @updateTime : [2022-12-25 19:58]
 * @updateRemark : [说明本次修改内容]
 */
package cn.edu.whut.sept.zuul.protoBuf;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@ProtobufClass
@NoArgsConstructor
@Data
@AllArgsConstructor
public class RoomProtoBuf {
    String name;
    List<String> items;
}
