package org.xmetaki.knife.bio.utils;

import java.io.IOException;
import java.io.InputStream;

public class IOReadUtil {

    // 从InputStream中读取固定长度的数据
    public static boolean readFixedLength(InputStream inputStream, byte[] bytes) throws IOException{
        return readFixedLength(inputStream, bytes, bytes.length);
    }

    // 从InputStream中读取固定长度的数据
    public static boolean readFixedLength(InputStream inputStream, byte[] buffer, int length) throws IOException {
        int readLength = 0;
        while (readLength < length) {
            int count = inputStream.read(buffer, readLength, length - readLength);
            if (count == -1) {
                return false;
            }
            readLength += count;
            return true;
        }
        return false;
    }
}
