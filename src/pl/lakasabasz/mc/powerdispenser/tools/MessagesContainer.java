package pl.lakasabasz.mc.powerdispenser.tools;

import java.util.HashMap;
import java.util.Map;

public class MessagesContainer {
	
	private static Map<MessageType, String> messages;
	
	static {
		messages = new HashMap<MessageType, String>();
		messages.put(MessageType.INIT, "Plugin load complete");
		messages.put(MessageType.PERMISSION_ERROR, "You don't have permission to use this command");
	}
	
	public static String getMessage(MessageType mt) {
		return messages.get(mt);
	}
}
