package com.kayoxu.things

import android.app.Activity
import android.os.Bundle
import android.os.Message
import com.google.android.things.pio.Gpio
import com.kayoxu.things.tools.GpioTools
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

/**
 * Skeleton of an Android Things activity.
 *
 * Android Things peripheral APIs are accessible through the class
 * PeripheralManagerService. For example, the snippet below will open a GPIO pin and
 * set it to HIGH:
 *
 * <pre>{@code
 * val service = PeripheralManagerService()
 * val mLedGpio = service.openGpio("BCM6")
 * mLedGpio.setDirection(Gpio.DIRECTION_OUT_INITIALLY_LOW)
 * mLedGpio.value = true
 * }</pre>
 * <p>
 * For more complex peripherals, look for an existing user-space driver, or implement one if none
 * is available.
 *
 * @see <a href="https://github.com/androidthings/contrib-drivers#readme">https://github.com/androidthings/contrib-drivers#readme</a>
 *
 */
class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val bcm4 = GpioTools.openGpio("BCM4")
        if (bcm4 != null) {
            val eService = Executors.newSingleThreadScheduledExecutor()
            eService.scheduleAtFixedRate(Runnable {
                bcm4.setDirection(if (bcm4.value) Gpio.DIRECTION_OUT_INITIALLY_LOW else Gpio.DIRECTION_OUT_INITIALLY_HIGH)
            }, 0, 200, TimeUnit.MILLISECONDS)
        }

        val msg = Message()
        ThingsApplication().mHandler.sendMessage(msg)

    }
}
