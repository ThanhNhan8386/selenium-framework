package bai5_config.utils;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigReader {
    private static ConfigReader instance;
    private Properties properties;

    private ConfigReader() {
        properties = new Properties();
        String env = System.getProperty("env", "dev");
        String fileName = "config-" + env + ".properties";
        try (java.io.InputStream is = ConfigReader.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is != null) {
                properties.load(is);
                System.out.println("[ConfigReader] Đang dùng môi trường: " + env);
            } else {
                System.out.println("Lỗi load properties: Không tìm thấy file " + fileName);
            }
        } catch (Exception e) {
            System.out.println("Lỗi load properties file: " + e.getMessage());
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url", "https://www.saucedemo.com");
    }

    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "15"));
    }

    public int getRetryCount() {
        return Integer.parseInt(properties.getProperty("retry.count", "1"));
    }
}

