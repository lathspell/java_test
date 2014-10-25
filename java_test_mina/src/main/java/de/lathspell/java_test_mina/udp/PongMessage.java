package de.lathspell.java_test_mina.udp;

public class PongMessage {

    short serial;

    public PongMessage(short serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "PongMessage(" + getSerial() + ")";
    }

    public short getSerial() {
        return serial;
    }

    public void setSerial(short serial) {
        this.serial = serial;
    }
}
