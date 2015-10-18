package at.arz.latte.framework.websockets;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.ejb.Singleton;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import at.arz.latte.framework.websockets.decoders.WebsocketMessageDecoder;
import at.arz.latte.framework.websockets.encoders.WebsocketMessageEncoder;
import at.arz.latte.framework.websockets.models.WebsocketMessage;

/**
 * websocket management
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
@Singleton
@ServerEndpoint(value = "/ws",
				encoders = WebsocketMessageEncoder.class,
				decoders = WebsocketMessageDecoder.class)
public class WebsocketEndpoint {

	private static Set<Session> sessions = Collections.synchronizedSet(new HashSet<Session>());

	@OnOpen
	public void opened(Session session) {
		sessions.add(session);
	}

	@OnClose
	public void closed(Session session) {
		sessions.remove(session);
	}

	@OnMessage
	public void chat(Session session, WebsocketMessage message) {
		for (Session client : sessions) {
			if (!client.equals(session)) {
				client.getAsyncRemote().sendObject(message);
			}
		}
	}

	public void chat(WebsocketMessage message) {
		for (Session client : sessions) {
			client.getAsyncRemote().sendObject(message);
		}
	}

}
