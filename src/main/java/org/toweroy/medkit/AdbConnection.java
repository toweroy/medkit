package org.toweroy.medkit;

import com.android.ddmlib.AndroidDebugBridge;
import com.android.ddmlib.IDevice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class AdbConnection {

    private static final String ADB_PATH_PROP = "adb.path";
    private static final Logger LOG = LoggerFactory.getLogger(AdbConnection.class);
    private List<Device> devices = new ArrayList<>();

    public AdbConnection() {

    }

    public void initializeADBConnection() {

        try {
            AndroidDebugBridge.initIfNeeded(false);
        } catch (IllegalStateException e) {
            LOG.error("Exception when initializing AndroidDebugBridge", e);
        }

        AndroidDebugBridge bridge = AndroidDebugBridge.getBridge();

        if (bridge == null) {
            bridge = AndroidDebugBridge.createBridge(
                    Properties.getString(ADB_PATH_PROP),
                    false);
        }

        if (bridge.isConnected() && bridge.hasInitialDeviceList()) {
            IDevice[] connectedDevices = bridge.getDevices();

            for (int i = 0; i < connectedDevices.length; i++) {
                Device device = new Device(connectedDevices[i]);

                devices.add(device);
//                notifyListenersAddedDevice(tDevice);
            }
        }

//        AndroidDebugBridge.addDeviceChangeListener(this);
    }
}
