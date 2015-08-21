package at.arz.latte.framework.websockets.models;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;

public class ChatMessage {
	
	private String message;
	
	private String sender;
	
	public ChatMessage() {}
	
	public ChatMessage(String message, String sender) {
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

	public static ChatMessage fromJSON(String message) {
		JsonObject json = 
				Json.createReader(
						new StringReader(message))
							.readObject();
		
		ChatMessage result = new ChatMessage(
				json.getString("message"),
				json.getString("sender"));
		
		return result;
	}
	
}
