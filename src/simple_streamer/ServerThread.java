package simple_streamer;

import java.io.*;
import java.net.*;

public class ServerThread implements Runnable {

	/*
	 * Main logic
	 */
	@Override
	public void run() {
		int port = 6262;
		ServerSocket serverSocket;
		try {
			while (true) {
				serverSocket = new ServerSocket(port);
				System.out.println("Waiting for client on port "
						+ serverSocket.getLocalPort() + "...");
				Socket server = serverSocket.accept();
				System.out.println("Connected to "
						+ server.getRemoteSocketAddress());

				// Send JSON features message
				PrintWriter toClient = new PrintWriter(
						server.getOutputStream(), true);
				String JSONMsg = "";
				toClient.print(JSONMsg);

				// Receive client requests
				BufferedReader fromClient = new BufferedReader(
						new InputStreamReader(server.getInputStream()));
				String clientMsg = fromClient.readLine();
			}
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			System.exit(-1);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(-1);
		}
	}
}
