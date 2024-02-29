package org.xmetaki.knife.bio.utils;

/**
 * MESSAGE FLAG
 */
public class MessageType {
    /**
     * login data format
     * [1] byte client login flag
     * [2] byte password length (encrypted)
     * [3~n] byte password (encrypted)
     * [n~n+4] byte listen port info length
     * [n+5~n+5+listen port info length] byte listen port info
     */
    public static final int LOGIN_EVENT = 1;


    // user create connection flag
    public static final int CONNECT_EVENT = 2;

    // user close flag
    public static final int CLOSE_EVENT = 3;

    // forward flag
    public static final int FORWARD_EVENT = 4;

    // heart beat flag
    public static final int HEART_BEAT_EVENT = 5;

    public String getComment(int flag) {
        switch(flag) {
            case LOGIN_EVENT:
                return "[登录事件]:";
            case CONNECT_EVENT:
                return "[连接事件]:";
            case CLOSE_EVENT:
                return "[关闭事件]:";
            case FORWARD_EVENT:
                return "[转发事件]:";
            case HEART_BEAT_EVENT:
                return "[心跳事件]:";
            default: return "INVALID FLAG !";
        }
}
}
