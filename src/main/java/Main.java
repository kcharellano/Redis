import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Main {
	public static void main(String[] args) {
		System.out.println("Initializing Redis Server!");
		ServerSocket serverSocket = null;
		int port = 6379;
		Socket clientSocket = null;
		try {
			serverSocket = new ServerSocket(port);
			serverSocket.setReuseAddress(true);
			while (true) {
				Thread thread = new Thread(new ClientHandler(serverSocket.accept()));
				thread.start();
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		} finally {
			try {
				System.out.println("Closing sockets");
				if (serverSocket != null) {
					serverSocket.close();
				}
				if (clientSocket != null) {
					clientSocket.close();
				}
			} catch (IOException e) {
				System.out.println("IOException: " + e.getMessage());
			}
		}
	}
}
