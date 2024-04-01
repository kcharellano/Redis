import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class RedisOutputStream extends OutputStreamWriter {

	public RedisOutputStream(OutputStream os) {
		super(os);
	}

	public void writeChar(char c) throws IOException {
		write(c);
	}


	public void writeCrLf() throws IOException {
		write("\r\n");
	}

	public void writeStringCrLf(String s) throws IOException {
		write(s);
		writeCrLf();
	}
}
