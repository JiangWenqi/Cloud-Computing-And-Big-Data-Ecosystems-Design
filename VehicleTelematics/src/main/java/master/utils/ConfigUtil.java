package master.utils;

import java.util.Properties;
/** @author Wenqi Description: read configuration */
public class ConfigUtil {

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
   * load configuration file
   *
   * @param path configuration path
   */
  private void loadConfig(String path) {
    Properties properties = new Properties();
    try {
      properties.load(this.getClass().getClassLoader().getResourceAsStream(path));
      this.properties = properties;
    } catch (Exception e) {
      throw new NullPointerException("Can't find path:" + path);
    }
  }

  public String getProperty(String keyName) {
    return properties.getProperty(keyName);
  }

  public Properties getProperties() {
    return properties;
  }
}
