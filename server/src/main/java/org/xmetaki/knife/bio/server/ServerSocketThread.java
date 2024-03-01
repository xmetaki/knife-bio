package org.xmetaki.knife.bio.server;

import ch.qos.logback.classic.Logger;
import org.omg.IOP.IOR;
import org.xmetaki.knife.bio.utils.AESUtil;
import org.xmetaki.knife.bio.utils.IOReadUtil;
import org.xmetaki.knife.bio.utils.MessageType;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.SocketAddress;

public class ServerSocketThread implements Runnable{
    // 客户端socket
    Socket socket;

    // 服务端token
    final String token;
    final AESUtil aesUtil;
    // 当前socket是否已经登录
    private boolean isLogin = false;

    private Logger logger = Server.logger;
    public ServerSocketThread(Socket socket, BaseInfo baseInfo){
        this.socket = socket;
        this.token = baseInfo.getToken();
        this.aesUtil = new AESUtil();
    }

    private int loginHandler(InputStream inputStream) throws IOException {
        int encryptedLength = inputStream.read();
        if (encryptedLength <= 0) {
            logger.warn("客户端发送的密码长度不合法");
            return -1;
        }

        byte[] pwdArr = new byte[encryptedLength];
        if (!IOReadUtil.readFixedLength(inputStream, pwdArr)) {
           logger.warn("server 密码读取异常");
           return -1;
        }
        byte[] decrypt = this.aesUtil.decrypt(pwdArr);

        if (! new String(decrypt).equals(token)) {
            logger.warn("server 密码不正确");
            return -1;
        }
        isLogin = true; // 直至此处判别为登录成功
        logger.info("{} 登录成功", socket.getRemoteSocketAddress());
        return 0;
    }

    @Override
    public void run() {
        String socketAddr = socket.getRemoteSocketAddress().toString();
        // BIO处理socket请求直至socket异常关闭
        while (socket.isConnected() && !socket.isClosed()) {
            try (InputStream inputStream = socket.getInputStream();) {
                int read = inputStream.read();
                if (read == -1) {
                    logger.info("客户端 {} 已经关闭了连接", socketAddr);
                    break;
                }
                // 没有登录需要先登录
                if (!isLogin)  {
                   if (read == MessageType.LOGIN_EVENT) {
                       logger.info("客户端开始登录");
                       int listenCount = loginHandler(inputStream);
                       if (!isLogin) {
                           logger.warn("客户端{} 本次登录失败, 将关闭此次连接", socketAddr);
                           break;
                       }
                       if (listenCount <= 0) {
                          logger.info("Server 客户端无监听事件, 将关闭此次连接");
                          break;
                       }
                       logger.info("server 共监听了 {}个接口", listenCount);
                   } else {
                       logger.warn("检查到客户端{}未登录, 将关闭此次连接", socketAddr);
                       break;
                   }
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        try {
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("针对 {}的连接已关闭", socketAddr);
    }

}
