import java.io.IOException;

public class Main {
	public static void main(String[] args) {
		try {
			RedisMain.submain(args);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
