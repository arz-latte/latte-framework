package at.arz.latte.framework.websockets.models;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;

/**
 * message for websocket communication
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class WebsocketMessage {
	
	private String message;
	
	private String sender;
	
	public WebsocketMessage() {}
	
	public WebsocketMessage(String message, String sender) {
		super();
		this.message = message;
		this.sender = sender;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}
	
	public String toJSON() {
		
		JsonObject json = 
				Json.createObjectBuilder()
					.add("message", getMessage())
					.add("sender", getSender())
					.build();
		
		return json.toString();
	}

	public static WebsocketMessage fromJSON(String message) {
		JsonObject json = 
				Json.createReader(
						new StringReader(message))
							.readObject();
		
		WebsocketMessage result = new WebsocketMessage(
				json.getString("message"),
				json.getString("sender"));
		
		return result;
	}
	
}
