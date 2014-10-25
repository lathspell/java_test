package de.lathspell.java_test_mina.udp;

public class PingMessage {

    private short serial;

    public PingMessage(short serial) {
        this.serial = serial;
    }

    @Override
    public String toString() {
        return "PingMessage(" + getSerial() + ")";
    }

    public short getSerial() {
        return serial;
    }

    public void setSerial(short serial) {
        this.serial = serial;
    }
}
