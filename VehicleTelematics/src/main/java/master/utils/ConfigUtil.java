package master.utils;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Properties;
/**
 *
 * @author Wenqi
 * Description: read configuration
 */

public class ConfigUtil {

    private static final Logger LOGGER = LogManager.getLogger(ConfigUtil.class);
    private String defaultPath = "parameters.properties";
    private Properties properties = null;
    public ConfigUtil() {
        loadConfig(defaultPath);
    }

    public ConfigUtil(String path) {
        this.defaultPath = path;
        loadConfig(defaultPath);
    }

    /**
     *
     * load configuration file
     *
     * @param path configuration path
     */
    private void loadConfig(String path) {
        Properties properties = new Properties();
        try {
            properties.load(this.getClass().getClassLoader().getResourceAsStream(path));
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
        }
        this.properties = properties;
    }

    /**
     * 根据关键字拿配置信息
     *
     * @param keyName 关键字
     * @return 配置项
     */
    public String getProperty(String keyName) {
        return properties.getProperty(keyName);
    }

    /**
     * 直接获取配置文件
     *
     * @return 配置文件
     */
    public Properties getProperties() {
        return properties;
    }
}
