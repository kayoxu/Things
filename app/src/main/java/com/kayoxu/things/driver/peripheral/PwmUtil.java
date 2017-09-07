package com.kayoxu.things.driver.peripheral;

import com.google.android.things.pio.PeripheralManagerService;
import com.google.android.things.pio.Pwm;

import java.io.IOException;

/**
 * Created by KayoXu on 2017/8/25.
 */

public class PwmUtil extends BasePeripheral {

    public static Pwm openPwm(String pwmName) {
        PeripheralManagerService managerSerive = getManagerSerive();
        try {
            Pwm pwm = managerSerive.openPwm(pwmName);
            return pwm;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void closePwm(Pwm pwm) {
        if (pwm != null) {
            try {
                pwm.close();
            } catch (IOException e) {
                e.printStackTrace();
                pwm = null;
            }
            pwm = null;
        }
    }

}
