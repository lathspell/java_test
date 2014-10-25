package de.lathspell.java_test_mina.udp2;

public class MyProtocol {

    /** Ping */
    public static final short PING_SIGNATURE = 0x3033;
    public static final int PING_SIZE = 96;
    public static final int PING_OFFSET_SERIAL = 20;
    /** Pong */
    public static final short PONG_SIGNATURE = 0x3032;
    public static final int PONG_SIZE = 24;
    public static final int PONG_OFFSET_SERIAL = 8;
}
