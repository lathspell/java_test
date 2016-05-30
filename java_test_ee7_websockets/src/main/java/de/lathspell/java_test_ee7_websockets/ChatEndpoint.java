package de.lathspell.java_test_ee7_websockets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ServerEndpoint(value = "/chat/{room}", encoders = ChatMessageEncoder.class, decoders = ChatMessageDecoder.class)
public class ChatEndpoint {

    private static final Logger log = LoggerFactory.getLogger(ChatEndpoint.class);

    private static final Set<Session> peers = Collections.synchronizedSet(new HashSet<Session>());

    @OnError
    public void onError(Session session, Throwable e) {
        log.info("onError for " + session + ": " + e);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("room") String room) {
        log.info("onOpen for " + session);
        session.getUserProperties().put("room", room);
        peers.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        log.info("onClose for " + session);
        peers.remove(session);
    }

    @OnMessage
    public void onMessage(Session session, ChatMessage chatMessage) throws Exception {
        log.info("onMessage ({} online) for {}: {}", peers.size(), session, chatMessage);

        String room = (String) session.getUserProperties().get("room");
        for (Session s : session.getOpenSessions()) {
            if (s.isOpen() && room.equals(s.getUserProperties().get("room"))) {
                s.getBasicRemote().sendObject(new ObjectMapper().writeValueAsString(chatMessage));
            }
        }
    }
}
