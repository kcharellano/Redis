import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Protocol {
	public static final char ASTERISK = '*';
	public static final char DOLLAR = '$';
	public static final char PLUS = '+';

	public static Object parseRESP(RedisInputStream ris) {
		final char respType = ris.readChar();
		switch (respType) {
			case ASTERISK:
				return parseArray(ris);
			case DOLLAR:
				return parseBulkString(ris);
			default:
				throw new RuntimeException("Unknown input: <" + (int) respType + "> is not a valid RESP type");
		}
	}
	private static List<String> parseArray(RedisInputStream ris) {
		final int arrayLength = ris.readIntCrLf();
		if (arrayLength < 0) {
			throw new RuntimeException("Array length cannot be negative");
		}
		List<String> command = new ArrayList<>(arrayLength);
		for (int i = 0; i < arrayLength; i++) {
			command.add( (String) parseRESP(ris));
		}
		return command;
	}

	private static String parseBulkString(RedisInputStream ris) {
		final int bulkStringLength = ris.readIntCrLf();
		if (bulkStringLength < 0) {
			throw new RuntimeException("Bulk string length cannot be negative");
		}
		return ris.readStringCrLf();
	}

	public static void writeSimpleString(RedisOutputStream ros, String message) {
		try {
			ros.writeChar(PLUS);
			ros.writeStringCrLf(message);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void writeBulkString(RedisOutputStream ros, String message) {
		if (message == null) {
			writeNullBulkString(ros);
		} else {
			try {
				ros.writeChar(DOLLAR);
				ros.writeStringCrLf(Integer.toString(message.length()));
				ros.writeStringCrLf(message);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

	}

	private static void writeNullBulkString(RedisOutputStream ros) {
		try {
			ros.writeStringCrLf("$-1");
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

}
