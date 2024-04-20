import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RedisMain {
	private static final Logger LOGGER = LoggerInitializer.init(RedisMain.class);
	private static final int port = 6379;

	public static void submain(String[] args) throws IOException {
		LOGGER.log(Level.INFO, "Starting server on port: " + port);
		ServerSocket serverSocket = new ServerSocket(port);
		serverSocket.setReuseAddress(true);

		LOGGER.log(Level.INFO, "Starting user input listener ...");
		Thread userInputThread = new Thread(new UserInputListener(serverSocket));
		userInputThread.start();

		while (!serverSocket.isClosed()) {
			new Thread(new ClientHandler(serverSocket.accept())).start();
		}
	}
}
