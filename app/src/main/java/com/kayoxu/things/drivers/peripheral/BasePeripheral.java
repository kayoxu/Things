package com.kayoxu.things.drivers.peripheral;

import com.google.android.things.pio.PeripheralManagerService;

/**
 * Created by KayoXu on 2017/8/25.
 */

class BasePeripheral {
    private static PeripheralManagerService service;

    synchronized static PeripheralManagerService getManagerSerive() {
        if (service == null) {
            service = new PeripheralManagerService();
        }
        return service;
    }
}
