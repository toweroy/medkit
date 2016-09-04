package org.toweroy.medkit;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class Properties {

    private static Config adbConfig;
    private static Config mainConfig;

    static {
        mainConfig = ConfigFactory.load("main.conf");
        adbConfig = ConfigFactory.load("adb.conf");
    }

    public static Object getObject(String property) {
        return adbConfig.getValue(property);
    }

    public static String getString(String property) {
        return mainConfig.getString(property);
    }
}
