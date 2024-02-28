package org.xmetaki.knife.bio.server;

import java.io.InputStream;
import java.net.Socket;

public class ServerSocketThread implements Runnable{
    Socket socket;
    String token;
    public ServerSocketThread(Socket socket, BaseInfo baseInfo){
        this.socket = socket;
        this.token = baseInfo.getToken();
    }
    @Override
    public void run() {
        try (
            InputStream inputStream = socket.getInputStream();
            ){
            byte[] bytes = new byte[1024];
            int len;
            while ((len = inputStream.read(bytes)) != -1) {
                String str = new String(bytes, 0, len);
                System.out.println("["+Thread.currentThread().getName()+"]"+"Server: 收到来自" + socket.getRemoteSocketAddress() + "的消息: " + str);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
