package at.arz.latte.framework.websockets;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

/**
 * json encoder for websocket communication
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class WebsocketMessageEncoder implements Encoder.Text<WebsocketMessage> {

	@Override
	public void destroy() {
	}

	@Override
	public void init(EndpointConfig arg0) {
	}

	@Override
	public String encode(WebsocketMessage message) throws EncodeException {
		return message.toJSON();
	}

}
