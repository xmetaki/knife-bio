package org.xmetaki.knife.bio.server;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.sun.org.apache.xerces.internal.impl.dv.xs.BaseDVFactory;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class Server {
    public static String DEFAULT_TOKEN = "123456";
    public static String config_path = "";
    static Logger logger;

    static {
       LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
       logger = loggerContext.getLogger("root");
    }

    public static void main(String[] args) {
        config_path = "/config.server.yaml";
        // 解析参数
        argumentParser(args);
        BaseInfo baseInfo = configFileParse(config_path);
        new Thread(new ServerThread(baseInfo)).start();
        System.out.println("启动成功啦!");
    }
    public static void argumentParser(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String arg = args[i];
           if (arg.equals("trace"))  {
               logger.setLevel(Level.TRACE);
           } else if (arg.equals("info")) {
               logger.setLevel(Level.INFO);
           }
           if (arg.endsWith(".yaml")) {
               config_path = arg;
           }
        }
    }

    public static BaseInfo configFileParse(String path) {
        BaseInfo baseInfo = new BaseInfo();
        URL url = Server.class.getResource(path);
        File file = new File(url.getFile());
        try (FileInputStream in = new FileInputStream(file);) {
            Yaml yaml = new Yaml();
            Map map = yaml.loadAs(in, Map.class);
            Map common = (Map)map.get("common");
            baseInfo.setBind_port((Integer) common.get("bind_port"));
            String token = (String) common.get("token");
            baseInfo.setToken(token == null ? DEFAULT_TOKEN : token);
            return baseInfo;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
