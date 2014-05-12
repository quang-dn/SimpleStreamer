package simple_streamer;

import java.io.*;
import java.net.*;

public class ClientThread implements Runnable {
	private String host = "localhost";
	private int port = 6262;
	private Socket socket = null;
	
	public ClientThread() {
		try {
			socket = new Socket(host, port);
		    System.out.println("Connection Established");
		} catch (UnknownHostException e) {
		     System.out.println("Socket: "+ e.getMessage());
		} catch (EOFException e) {
		     System.out.println("EOF: "+ e.getMessage());
		} catch (IOException e) {
		     System.out.println("readline: "+ e.getMessage());
		} finally {
		     if (socket != null) try {
		       socket.close();
		     } catch (IOException e) {
		       System.out.println("close: "+ e.getMessage());
		     }
		}	
	}
	
	@Override
	public void run() {
		
	}

}
