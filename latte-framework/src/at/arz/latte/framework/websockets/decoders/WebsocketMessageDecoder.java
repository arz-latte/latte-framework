package at.arz.latte.framework.websockets.decoders;

import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;

import at.arz.latte.framework.websockets.models.WebsocketMessage;

/**
 * json decoder for websocket communication
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class WebsocketMessageDecoder implements Decoder.Text<WebsocketMessage> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	@Override
	public WebsocketMessage decode(String json) throws DecodeException {
		return WebsocketMessage.fromJSON(json);
	}

	@Override
	public boolean willDecode(String arg0) {
		return true;
	}

}
