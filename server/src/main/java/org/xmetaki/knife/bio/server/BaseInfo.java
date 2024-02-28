package org.xmetaki.knife.bio.server;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseInfo {
    Integer bind_port; // 服务端监听的端口
    String token; //服务端链接端口需要的token信息
}
