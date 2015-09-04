package at.arz.latte.framework.websockets.models;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

/**
 * message for websocket communication
 * 
 * Dominik Neuner {@link "mailto:dominik@neuner-it.at"}
 *
 */
public class WebsocketMessage {

	private String message;

	private String moduleId;

	public WebsocketMessage() {
	}

	public WebsocketMessage(String message) {
		this();
		this.message = message;
	}

	public WebsocketMessage(String message, String moduleId) {
		this(message);
		this.moduleId = moduleId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public String toJSON() {

		JsonObjectBuilder json = Json.createObjectBuilder().add("message", getMessage());

		if (moduleId != null) {
			json.add("module-id", getModuleId());
		}

		return json.build().toString();
	}

	public static WebsocketMessage fromJSON(String message) {
		JsonObject json = Json.createReader(new StringReader(message)).readObject();

		WebsocketMessage result = new WebsocketMessage(json.getString("message"));

		if (json.containsKey("module-id")) {
			result.setModuleId(json.getString("module-id"));
		}

		return result;
	}

}
