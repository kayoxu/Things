package com.kayoxu.things.driver.manager;

import com.google.android.things.pio.SpiDevice;

import java.io.IOException;

/**
 * Created by KayoXu on 2017/9/7.
 */

public class SpiDeviceManager {
    private static final String TAG = "SpiDeviceManager";
    private SpiDevice spiDevice;
//    private Gpio resetPin;
//    private int busSpeed = 1000000;


    public SpiDeviceManager(SpiDevice device) {
        this.spiDevice = spiDevice;
    }

    /**
     * @param address 寄存器地址
     * @param value   要写入的值
     */
    public void writeRegister(byte address, byte value) {
        byte buffer[] = {(byte) (((address << 1) & 0x7E)), value};
        byte response[] = new byte[buffer.length];
        try {
            spiDevice.transfer(buffer, response, buffer.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param address 寄存器地址
     * @return 读取的值
     */
    public byte readRegister(byte address) {
        byte buffer[] = {(byte) (((address << 1) & 0x7E) | 0x80), 0};
        byte response[] = new byte[buffer.length];
        try {
            spiDevice.transfer(buffer, response, buffer.length);
            return response[1];
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * Sets the bits of a register according to a bit mask
     * This allows turning on only specific bytes of a register without altering the rest
     *
     * @param address The register's address
     * @param mask    The mask to apply
     */
    public void setBitMask(byte address, byte mask) {
        byte value = readRegister(address);
        writeRegister(address, (byte) (value | mask));
    }


    /**
     * Clears the bits of a register according to a bit mask
     * This allows turning off only specific bytes of a register without altering the rest
     *
     * @param address The register's address
     * @param mask    The mask to apply
     */
    public void clearBitMask(byte address, byte mask) {
        byte value = readRegister(address);
        writeRegister(address, (byte) (value & (~mask)));
    }

}
