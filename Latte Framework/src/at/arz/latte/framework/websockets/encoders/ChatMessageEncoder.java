package at.arz.latte.framework.websockets.encoders;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import at.arz.latte.framework.websockets.models.ChatMessage;

public class ChatMessageEncoder implements Encoder.Text<ChatMessage> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	@Override
	public String encode(ChatMessage message) throws EncodeException {
		return message.toJSON();
	}

}
