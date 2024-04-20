import java.io.IOException;
import java.net.ServerSocket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserInputListener implements Runnable {
	private static final Logger LOGGER = LoggerInitializer.init(UserInputListener.class);

	private final ServerSocket serverSocket;

	public UserInputListener(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		LOGGER.log(Level.INFO, "Type 'exit' to shutdown the server");
		Scanner scanner = new Scanner(System.in);
		while (scanner.hasNextLine()) {
			String input = scanner.nextLine();
			if ("exit".equals(input)) {
				try {
					LOGGER.log(Level.INFO, "User initiated shutdown -- Closing server socket ...");
					serverSocket.close();
				} catch (IOException e) {
					LOGGER.log(Level.SEVERE, e.toString(), e);
				}
				break;
			}
		}
		scanner.close();
	}
}
