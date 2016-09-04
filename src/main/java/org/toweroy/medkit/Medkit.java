package org.toweroy.medkit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;

public class Medkit {

    private static final Logger LOG = LoggerFactory.getLogger(Medkit.class);
    private AdbConnection adbConnection;

    public static void main(String[] args) {
        get("/adb/properties", (req, res) -> getAdbProperties());
    }

    private void initializeAdb() {
        adbConnection = new AdbConnection();
        adbConnection.initializeADBConnection();
    }

    public static Object getAdbProperties() {
        return Properties.getObject("adb.property");
    }

}
