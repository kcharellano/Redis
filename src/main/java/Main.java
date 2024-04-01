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
			clientSocket = serverSocket.accept();
			System.out.println("client socket = " + clientSocket.getPort() + " connected!");
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
