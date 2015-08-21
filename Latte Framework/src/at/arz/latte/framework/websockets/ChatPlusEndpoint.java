package at.arz.latte.framework.websockets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Singleton;
import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import at.arz.latte.framework.websockets.decoders.ChatMessageDecoder;
import at.arz.latte.framework.websockets.encoders.ChatMessageEncoder;
import at.arz.latte.framework.websockets.models.ChatMessage;

@Singleton
@ServerEndpoint(value = "/chatplus",
		encoders = ChatMessageEncoder.class,
		decoders = ChatMessageDecoder.class)
public class ChatPlusEndpoint {

	private static Set<Session> sessions = 
			Collections.synchronizedSet(new HashSet<Session>());
	
	@OnOpen
	public void opened(Session session) {
		sessions.add(session);
	}
	
	@OnClose
	public void closed(Session session) {
		sessions.remove(session);
	}
	
	@OnMessage
	public void chat(Session session, ChatMessage message) {
		for(Session client : sessions) {
			if (!client.equals(session)) {
				client.getAsyncRemote().sendObject(message);
			}
		}
	}
	
	public void chat(ChatMessage message) {
		for(Session client : sessions) {
			client.getAsyncRemote().sendObject(message);
		}
	}
	
}
