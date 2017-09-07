package com.kayoxu.things.driver.peripheral;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.PeripheralManagerService;

import java.io.IOException;

/**
 * Created by KayoXu on 2017/8/25.
 */

public class GpioUtil extends BasePeripheral {

    public static Gpio openGpio(String gpioName) {
        PeripheralManagerService managerSerive = getManagerSerive();
        try {
            Gpio gpio = managerSerive.openGpio(gpioName);
            if (gpio != null) {
                return gpio;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    public static void closeGpio(Gpio gpio) {
        if (gpio != null) {
            try {
                gpio.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                gpio = null;
            }
            gpio = null;
        }
    }


    public static void setGpioValue(Gpio gpio, boolean value) {
        if (gpio != null) {
            try {
                gpio.setValue(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean getGpioValue(Gpio gpio) {
        if (gpio != null) {
            try {
                return gpio.getValue();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }


}
