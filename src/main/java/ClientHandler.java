import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ClientHandler implements Runnable {

	private final Socket clientSocket;

	public ClientHandler(Socket clientSocket) {
		this.clientSocket = clientSocket;
	}

	@Override
	public void run() {
		try {
			RedisOutputStream ros = new RedisOutputStream(clientSocket.getOutputStream());
			while (true) {
				RedisInputStream ris = new RedisInputStream(clientSocket.getInputStream());
				if (ris.isEndOfStream()) {
					break;
				}
				List<String> command = (ArrayList<String>) Protocol.parseRESP(ris);
				Processor.processCommand(command, ros);
				ros.flush(); // Ensure the response is sent immediately
			}
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		} finally {
			try {
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println("IOException: " + e.getMessage());
			}
		}
	}
}


