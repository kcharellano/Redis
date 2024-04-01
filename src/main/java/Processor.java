import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Processor {
	private static final String PING = "ping";
	private static final String ECHO = "echo";
	private static final String SET = "set";
	private static final String GET = "get";
	public static void processCommand(List<String> command, RedisOutputStream ros) {
		if (command.isEmpty()) {
			throw new RuntimeException("Cannot process empty command");
		}
		String keyword = command.get(0).toLowerCase();
		switch (keyword) {
			case PING:
				Protocol.writeSimpleString(ros, "PONG");
				break;
			case ECHO:
				if (command.size() != 2) {
					throw new RuntimeException("ECHO command must have exactly one argument");
				}
				Protocol.writeBulkString(ros, command.get(1));
				break;
			case SET:
				if (command.size() < 3) {
					throw new RuntimeException("SET command must have atleast two arguments");
				}
				SingletonMap.getInstance().set(command.get(1), command.get(2), getOptions(command));
				Protocol.writeSimpleString(ros, "OK");
				break;
			case GET:
				if (command.size() != 2) {
					throw new RuntimeException("GET command must have exactly one argument");
				}
				String value = SingletonMap.getInstance().get(command.get(1));
				Protocol.writeBulkString(ros, value);
				break;
			default:
				throw new RuntimeException("Unknown command: " + keyword);
		}
	}

	private static Map<String, Integer> getOptions(List<String> command) {
		Map<String, Integer> opts = new HashMap<>();
		for (int i = 3; i < command.size(); i = i + 2) {
				opts.put(command.get(i), Integer.parseInt(command.get(i + 1)));
		}
		if (opts.isEmpty()) {
			return null;
		}
		return opts;
	}
}
