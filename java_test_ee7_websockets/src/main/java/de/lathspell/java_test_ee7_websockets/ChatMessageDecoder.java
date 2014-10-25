package de.lathspell.java_test_ee7_websockets;

import java.io.StringReader;
import java.util.Date;

import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {

    @Override
    public void init(final EndpointConfig config) {
    }

    @Override
    public void destroy() {
    }

    @Override
    public ChatMessage decode(String textMessage) throws DecodeException {
        JsonObject obj = Json.createReader(new StringReader(textMessage)).readObject();

        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(obj.getString("message"));
        chatMessage.setReceived(new Date());
        return chatMessage;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }
}
