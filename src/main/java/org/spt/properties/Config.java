package org.spt.properties;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

/**
 * Created by ALFAYO on 7/17/2017.
 */
public class Config {
    private static Properties defaultProps = new Properties();
    static {
        try {

            String currentDir = System.getProperty("user.dir");
            System.out.println("Current dir using System:" +currentDir);


            /**
             * Realized relative paths were working inconsistently depending on how tomcat was started
             * Resorted to getting the config class path to derive the properties file path
             * */
            String path=Config.class.getProtectionDomain().getCodeSource().getLocation().getPath();
            String paths[]= path.split("webapps");
            String confFilePath=paths[0]+"/conf/spt.properties";
            String confAutho2Path=paths[0]+"/conf/kwatuhaspt.json";
            FileInputStream in = new FileInputStream(confFilePath);
            defaultProps.load(in);
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static String getProperty(String key) {
        return defaultProps.getProperty(key);
    }
}
