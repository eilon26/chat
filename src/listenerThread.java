
import java.net.ServerSocket;
/**
 * the listenerThread is responsible of waiting for another client to connect the server.
 * when another client is connected it open for him a socket
 * and start the serverThread thread
 * @param listener a ServerSocket type
 * @param serverGui a server type
 * @author EILON and DANIEL
 *
 */
public class listenerThread extends Thread {
	private ServerSocket listener;
	private server serverGui;
	/**
	 * the constructor of listenerThread by listener and serverGui
     * @param listener a ServerSocket type
     * @param serverGui a server type
	 */
	public listenerThread(server serverGui, ServerSocket listener) {
		this.serverGui = serverGui;
		this.listener = listener;
	}
	/**
	 * start a loop that wait for more client to connect
	 */
	@Override
	public void run(){
		try {
			while (true) {
				new serverThread(listener.accept(),serverGui).start();
			}
		} catch (Exception e) {
		} 
	}
}
