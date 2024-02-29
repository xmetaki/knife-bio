package org.xmetaki.knife.bio.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import static org.xmetaki.knife.bio.server.Server.logger;

@Slf4j
public class ServerThread implements Runnable{
    BaseInfo baseInfo;
    ServerSocket serverSocket;
    public ServerThread(BaseInfo baseInfo) {
       this.baseInfo = baseInfo;
       try {
           serverSocket = new ServerSocket(baseInfo.getBind_port());
       } catch (IOException e) {
           throw new RuntimeException(e);
       }
    }
    @Override
    public void run() {
        while(true) {
            logger.info("accept事件循环cycle");
            Socket accept = null;
            // 转换受检异常为runtime异常
            try {
                accept = serverSocket.accept();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            logger.info("Server: 接受来自{}的请求", accept.getRemoteSocketAddress());
            // BIO 只能开一个多线程了 连线程池都不能用:(
            new Thread(new ServerSocketThread(accept, baseInfo), "read-"+accept.getRemoteSocketAddress()).start();
        }
    }
}
