package com.kayoxu.things.driver.manager;

import com.google.android.things.pio.Gpio;
import com.google.android.things.pio.SpiDevice;

import java.io.IOException;

/**
 * Created by KayoXu on 2017/9/7.
 */

public class Rc522Manager extends SpiDeviceManager {

    private static final String TAG = "Rc522Manager";
    private SpiDevice spiDevice;
    private Gpio resetPin;
    private int busSpeed = 1000000;

    private byte[] uid;

    private byte[] backData;
    private int backDataLength;
    private int backLength;
    private static final byte MAX_LENGTH = 16;

    /**
     * Authentication using Key A
     */
    public static final byte AUTH_A = 0x60;
    /**
     * Authentication using Key B
     */
    public static final byte AUTH_B = 0x61;

    /* MFRC522 commands, found in Table 149, page 70 */
    private static final byte COMMAND_IDLE = 0x00;
    private static final byte COMMAND_CALCULATE_CRC = 0x03;
    private static final byte COMMAND_TRANSMIT = 0x04;
    private static final byte COMMAND_RECEIVE = 0x08;
    private static final byte COMMAND_TRANSCEIVE = 0x0C;
    private static final byte COMMAND_MF_AUTHENT = 0x0E;
    private static final byte COMMAND_SOFT_RESET = 0x0F;

    /* MIFARE commands */
    private static final byte COMMAND_READ = 0x30;
    private static final byte COMMAND_WRITE = (byte) 0xA0;
    private static final byte COMMAND_INCREMENT = (byte) 0xC1;
    private static final byte COMMAND_DECREMENT = (byte) 0xC0;
    private static final byte COMMAND_RESTORE = (byte) 0xC2;
    private static final byte COMMAND_TRANSFER = (byte) 0xb0;

    private static final byte COMMAND_REQUIRE_ID = 0x26;
    private static final byte COMMAND_REQUIRE_ALL = 0x52;
    private static final byte COMMAND_ANTICOLLISION = (byte) 0x93;
    private static final byte COMMAND_SELECT = (byte) 0x93;
    private static final byte COMMAND_END = 0x50;

    /* Found in table 20, page 36 */
    private static final byte REGISTER_COMMAND = 0x01; //CommandReg
    private static final byte REGISTER_INTERRUPT_ENABLE = 0x02; //ComIEnReg
    private static final byte REGISTER_COM_IRQ = 0x04; // DivIEnReg
    private static final byte REGISTER_DIV_IRQ = 0x05; //ComIrqReg
    private static final byte REGISTER_ERROR = 0x06; //ErrorReg
    private static final byte REGISTER_COMMUNICATION_STATUS = 0x07; //Status1Reg
    private static final byte REGISTER_RXTX_STATUS = 0x08; //Status2Reg
    private static final byte REGISTER_FIFO_DATA = 0x09; //FIFODataReg
    private static final byte REGISTER_FIFO_LEVEL = 0x0A; //FIFOLevelReg
    private static final byte REGISTER_CONTROL = 0x0C; //ControlReg
    private static final byte REGISTER_BIT_FRAMING = 0x0D; //BitFramingReg
    private static final byte REGISTER_MODE = 0x11; //ModeReg
    private static final byte REGISTER_TX_CONTROL = 0x14; //TxControlReg
    private static final byte REGISTER_TX_MODE = 0x15; //TxASKReg
    private static final byte REGISTER_CRC_RESULT_HIGH = 0x21; //CRCResultReg
    private static final byte REGISTER_CRC_RESULT_LOW = 0x22; //CRCResultReg
    private static final byte REGISTER_RF_CONFIG = 0x26; //RFCfgReg
    private static final byte REGISTER_TIMER_MODE = 0x2A; //TModeReg
    private static final byte REGISTER_TIMER_PRESCALER_MODE = 0x2B; //TPrescalerReg
    private static final byte REGISTER_TIMER_RELOAD_HIGH = 0x2C; //TReloadReg
    private static final byte REGISTER_TIMER_RELOAD_LOW = 0x2D; //TReloadReg


    public Rc522Manager(SpiDevice spiDevice, Gpio resetPin) throws IOException {
        super(spiDevice);
        this.spiDevice = spiDevice;
        this.resetPin = resetPin;

        init();
    }

    private void init() throws IOException {
        spiDevice.setFrequency(busSpeed);
        resetPin.setDirection(Gpio.DIRECTION_OUT_INITIALLY_HIGH);
        initSpiDevice();
    }

    private void initSpiDevice() {
        resetSpiDevice();
        writeRegister(REGISTER_TIMER_MODE, (byte) 0x8D);
        writeRegister(REGISTER_TIMER_PRESCALER_MODE, (byte) 0x3E);
        writeRegister(REGISTER_TIMER_RELOAD_LOW, (byte) 30);
        writeRegister(REGISTER_TIMER_RELOAD_HIGH, (byte) 0);
        writeRegister(REGISTER_TX_MODE, (byte) 0x40);
        writeRegister(REGISTER_MODE, (byte) 0x3D);
        setAntenna(true);


    }
    /**
     * Disables or enables the RC522's antenna
     *
     * @param enabled State to set the antenna to
     */
    private void setAntenna(boolean enabled) {
        if (enabled) {
            byte currentState = readRegister(REGISTER_TX_CONTROL);
            if ((currentState & 0x03) != 0x03) {
                setBitMask(REGISTER_TX_CONTROL, (byte) 0x03);
            }
        } else {
            clearBitMask(REGISTER_TX_CONTROL, (byte) 0x03);
        }
    }

    /**
     * Performs a soft resetSpiDevice on the Rc522
     */
    private void resetSpiDevice() {
        writeRegister(REGISTER_COMMAND, COMMAND_SOFT_RESET);
    }


    /**
     * Gets the UID of the last card that was successfully read. This may be empty if no card has
     * been read before.
     *
     * @return A byte array containing the card's UID.
     */
    public byte[] getUid() {
        return uid;
    }

    /**
     * Returns a string representation of the last read tag's UID
     *
     * @param separator The character that separates each element of the uid
     * @return A string representing the tag's UID
     */
    public String getUidString(String separator) {
        if (this.uid == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String prefix = "";
        for (byte b : this.uid) {
            int ubyte = b & 0xff;
            if (ubyte == 0) {
                break;
            }
            sb.append(prefix);
            prefix = separator;
            sb.append(ubyte);
        }
        return sb.toString();
    }

    /**
     * Returns a string representation of the last read tag's UID, separated by '-'
     */
    public String getUidString() {
        return getUidString("-");
    }
}
