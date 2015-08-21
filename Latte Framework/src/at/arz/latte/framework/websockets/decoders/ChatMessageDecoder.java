package at.arz.latte.framework.websockets.decoders;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import at.arz.latte.framework.websockets.models.ChatMessage;

public class ChatMessageDecoder implements Decoder.Text<ChatMessage> {

	@Override
	public void destroy() {	}

	@Override
	public void init(EndpointConfig arg0) { }

	@Override
	public ChatMessage decode(String json) throws DecodeException {
		return ChatMessage.fromJSON(json);
	}

	@Override
	public boolean willDecode(String arg0) {
		return true;
	}

}
