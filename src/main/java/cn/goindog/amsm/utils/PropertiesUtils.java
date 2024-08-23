package cn.goindog.amsm.utils;

import java.io.*;
import java.util.Properties;

public class PropertiesUtils {
    public static int server_port = 3365;
    public static String password_encode_key = "xjIh@da*jnxf*%jo@$HIUuhcrakn@xa3";

    static {
        File configFile = new File(".amsm", "config.properties");
        Properties properties = new Properties();
        if (configFile.exists()) {
            try {
                properties.load(new FileInputStream(configFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            server_port = Integer.parseInt(properties.getProperty("server_port"));
            password_encode_key = properties.getProperty("password_encode_key");
        } else {
            properties.setProperty("server_port", String.valueOf(server_port));
            properties.setProperty("password_encode_key", password_encode_key);
            try {
                properties.store(new FileOutputStream(configFile), null);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
