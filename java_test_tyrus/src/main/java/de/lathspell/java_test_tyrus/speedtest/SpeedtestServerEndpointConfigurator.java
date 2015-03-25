package de.lathspell.java_test_tyrus.speedtest;

import javax.websocket.HandshakeResponse;
import javax.websocket.server.HandshakeRequest;
import javax.websocket.server.ServerEndpointConfig;

public class SpeedtestServerEndpointConfigurator extends ServerEndpointConfig.Configurator {
    
    @Override
    public void modifyHandshake(ServerEndpointConfig sec, HandshakeRequest request, HandshakeResponse response) {
        if (request.getHeaders().containsKey("user-agent")) {
            sec.getUserProperties().put("user-agent", request.getHeaders().get("user-agent").get(0)); // lower-case!
        }
    }
}
