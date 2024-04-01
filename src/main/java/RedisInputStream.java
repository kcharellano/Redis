import java.io.InputStream;
import java.io.InputStreamReader;

public class RedisInputStream extends InputStreamReader {
	private static final int BUFFER_SIZE = 8192;
	private final char[] buffer;
	private int pos, limit;

	public RedisInputStream(InputStream in, int size) {
		super(in);
		buffer = new char[size];
		fill();
	}

	public RedisInputStream(InputStream in) {
		this(in, BUFFER_SIZE);
	}

	public char readChar() {
		return buffer[pos++];
	}

	public void printTotalBuffer() {
		StringBuffer sb = new StringBuffer();
		for (char c : buffer) {
			switch (c) {
				case '\r':
					sb.append("\\r");
					break;
				case '\n':
					sb.append("\\n");
					break;
				default:
					sb.append(c);
			}
		}
		System.out.println(sb.toString());
	}

	public void printRelativeBuffer() {
		StringBuffer sb = new StringBuffer();
		for (int i = pos; i < limit; i++) {
			char c = buffer[i];
			switch (c) {
				case '\r':
					sb.append("\\r");
					break;
				case '\n':
					sb.append("\\n");
					break;
				default:
					sb.append(c);
			}
		}
		System.out.println(sb.toString());
	}

	public int readIntCrLf() {
		int value = 0;
		boolean isNeg = false;
		char c = readChar();
		if (c == '-') {
			isNeg = true;
		} else {
			value = c - '0';
		}
		while (true) {
			c = readChar();
			if (c == '\r') {
				if (readChar() != '\n') {
					throw new RuntimeException("Expected LF after CR");
				}
				break;
			}
			value = value * 10 + (c - '0');
		}
		return isNeg ? -value : value;
	}

	public String readStringCrLf() {
		StringBuilder sb = new StringBuilder();
		while (true) {
			char c = readChar();
			if (c == '\r') {
				if (readChar() != '\n') {
					throw new RuntimeException("Expected LF after CR");
				}
				break;
			}
			sb.append(c);
		}
		return sb.toString();
	}

	public boolean isEndOfStream() {
		return limit == -1;
	}

	private void fill() {
		try {
			pos = 0;
			limit = read(buffer, 0, buffer.length);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
