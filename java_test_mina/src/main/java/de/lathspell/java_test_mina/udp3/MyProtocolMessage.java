package de.lathspell.java_test_mina.udp3;

import org.apache.mina.core.buffer.IoBuffer;

public interface MyProtocolMessage {

    public IoBuffer encode() throws Exception;
}
