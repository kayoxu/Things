package com.kayoxu.things.drivers.peripheral;

import com.google.android.things.pio.Pwm;

import java.io.IOException;

/**
 * Created by KayoXu on 2017/8/25.
 */

public class PwmTools extends PeripheralTools {

    public static Pwm openPwm(String pwmName) {
        getPwmManagerSerive();
        try {
            Pwm pwm = service.openPwm(pwmName);
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

    public static void initPwm(Pwm pwm) {

        if (pwm != null) {
            try {
                pwm.setPwmFrequencyHz(0.5);//设置频率 HZ
                pwm.setPwmDutyCycle(50.0);//设置占空比
                pwm.setEnabled(true);//是能pwm

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
