package com.kayoxu.things.tools;

import com.google.android.things.pio.PeripheralManagerService;

import java.util.List;

/**
 * Created by KayoXu on 2017/8/25.
 */

public class PeripheralTools {
    static PeripheralManagerService service;

    static void getGpioManagerSerive() {
        if (service == null) {
            service = new PeripheralManagerService();
        }
    }

    static void getI2cBusManagerSerive() {
        if (service == null) {
            service = new PeripheralManagerService();
        }
    }

    static void getI2sDeviceManagerSerive() {
        if (service == null) {
            service = new PeripheralManagerService();
        }
    }

    static void getPwmManagerSerive() {
        if (service == null) {
            service = new PeripheralManagerService();
        }
    }

}
