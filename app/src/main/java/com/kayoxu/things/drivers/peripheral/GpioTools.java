package com.kayoxu.things.drivers.peripheral;

import com.google.android.things.pio.Gpio;

import java.io.IOException;

/**
 * Created by KayoXu on 2017/8/25.
 */

public class GpioTools extends PeripheralTools {

    public static Gpio openGpio(String gpioName) {
        getGpioManagerSerive();
        try {
            Gpio gpio = service.openGpio(gpioName);
            if (gpio != null) {
                return gpio;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return null;
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

/*    private static void getGpios() {
        try {
            final Gpio bcm4 = service.openGpio("BCM4");
            bcm4.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW);

            Gpio bcm17 = service.openGpio("BCM17");
            bcm17.setDirection(Gpio.DIRECTION_IN);
            bcm17.setEdgeTriggerType(Gpio.EDGE_FALLING);
            bcm17.registerGpioCallback(new GpioCallback() {
                @Override
                public boolean onGpioEdge(Gpio gpio) {
                    setGpioValue(bcm4, !getGpioValue(gpio));
                    return true;//super.onGpioEdge(gpio);
                }
            });


        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/
}
