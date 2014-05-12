package simple_streamer;

import java.io.IOException;

import javax.swing.JFrame;

import org.apache.commons.codec.binary.Base64;
import org.bridj.Pointer;

import com.github.sarxos.webcam.ds.buildin.natives.Device;
import com.github.sarxos.webcam.ds.buildin.natives.DeviceList;
import com.github.sarxos.webcam.ds.buildin.natives.OpenIMAJGrabber;

import org.kohsuke.args4j.*;
import static org.kohsuke.args4j.ExampleMode.ALL;

public class EntryPoint {
	
	/*
	 * Instance variables represent command line arguments
	 */

	// -sport argument
	@Option(name = "-sport", required = false, 
			usage = "Specify server port to set up connection", metaVar = "SPORT")
	private int sport;

	// -remote argument
	@Option(name = "-remote", required = false, 
			usage = "Specify the host name of remote server to connect to", metaVar = "HOSTNAME")
	private String remoteHost;
	
	// -rport argument
	@Option(name = "-rport", required = false, 
			usage = "Specify the port of remote server to connect to", metaVar = "RPORT")
	private int rport;
	
	// -rate argument
	@Option(name = "-rate", required = false, 
			usage = "Specify the rate limit", metaVar = "RATE")
	private int rate;
	
	/**
	 * This method provides convenient way to parse command line arguments
	 * and validate them.
	 * @param args Command line arguments array
	 * @throws IOException
	 */
	public void parseArgs(String[] args) throws IOException {
		CmdLineParser parser = new CmdLineParser(this);

		try {
			// parse the arguments.
			parser.parseArgument(args);
			
			if (this.remoteHost != null) {
				if (this.rport == 0) {
					throw new CmdLineException(parser,"You have to specify port of remove server");
				}
			}
			else if (this.rport > 0) {
				if (this.remoteHost == null) {
					throw new CmdLineException(parser,"You have to specify host name of remove server");
				}
			}

		} catch (CmdLineException e) {
			// Print error message
			System.err.println(e.getMessage() + "\n");
			System.err
					.println("java -jar SimpleStreamer.jar [options...] arguments...");
			// print the list of available options
			parser.printUsage(System.err);
			System.err.println();

			// Print option sample
			System.err.println(" Example: java -jar SimpleStreamer.jar"
					+ parser.printExample(ALL));

			System.exit(-1);
		}
	}
	
	public void localMode() {
		
		// Setup GUI Component
		Viewer myViewer = new Viewer();
		JFrame frame = new JFrame("Simple Stream Viewer");
		frame.setVisible(true);
		frame.setSize(320, 240);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(myViewer);
		
		/**
		 * This example show how to use native OpenIMAJ API to capture raw bytes
		 * data as byte[] array. It also calculates current FPS.
		 */
		OpenIMAJGrabber grabber = new OpenIMAJGrabber();

		Device device = null;
		Pointer<DeviceList> devices = grabber.getVideoDevices();
		for (Device d : devices.get().asArrayList()) {
			device = d;
			break;
		}
		
		boolean started = grabber.startSession(320, 240, 30, Pointer.pointerTo(device));
		if (!started) {
			throw new RuntimeException("Not able to start native grabber!");
		}
		
		int n = 1000;
		int i = 0;
		do {
			/* Get a frame from the webcam. */
			grabber.nextFrame();
			/* Get the raw bytes of the frame. */
			byte[] raw_image=grabber.getImage().getBytes(320 * 240 * 3);
			/* Apply a crude kind of image compression. */
			byte[] compressed_image = Compressor.compress(raw_image);
			/* Prepare the date to be sent in a text friendly format. */
			byte[] base64_image = Base64.encodeBase64(compressed_image);
			/*
			 * The image data can be sent to connected clients.
			 */
			
			/*
			 * Assume we received some image data.
			 * Remove the text friendly encoding.
			 */
			byte[] nobase64_image = Base64.decodeBase64(base64_image);
			/* Decompress the image */
			byte[] decompressed_image = Compressor.decompress(nobase64_image);
			/* Give the raw image bytes to the viewer. */
			myViewer.ViewerInput(decompressed_image);
			frame.repaint();
		} while (++i < n);

		grabber.stopSession();
	}
	
	public void remoteMode() {
		
	}
	
	public static void main(String[] args) throws IOException {
		EntryPoint newEntry = new EntryPoint();
		newEntry.parseArgs(args);
		newEntry.localMode();
	}

}
