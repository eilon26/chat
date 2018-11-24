
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * the class clientThread is a thread that responsible of taking care the client and dealing 
 * with the server. it is broadcasting massages for the server and getting messages from the server 
 * by the agreed protocol. more over it analyze the information that it get from server and 
 * represent it on the client screen if needed.
 * @param client is ChatClient type. 
 * @author EILON and DANIEL
 *
 */
public class clientThread extends Thread {
	ChatClient client;
	/**
	 * the construclor of clientThread
	 * @param client is a ChatClient type  
	 */
	public clientThread(ChatClient client) {
		this.client=client;

	}


	/**
	 * Connects to the server then enters the processing loop.
	 */
	@Override
	public void run() {
		try {
			// Make connection and initialize streams
			while (client.getJToggleButton_connect().isEnabled()) {//will stay in the loop until the user press connect
				try {
					Thread.sleep(30);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			String serverAddress = client.getIp_ad().getText();
			Socket socket = new Socket(serverAddress, 9001);
			// Create character streams for the socket.
			client.in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			client.out = new PrintWriter(socket.getOutputStream(), true);
			// Process all messages from server, according to the protocol.
			while (true) {
				String line = client.in.readLine();
				if (line.startsWith("SUBMITNAME")) {
					client.out.println(client.getMy_name().getText());
				}else if (line.startsWith("RESUBMITNAME")) {
					client.getJTextArea_Main().append("invalid name. enter your name again\n");
					client.getJToggleButton_connect().setEnabled(true);
					while (client.getJToggleButton_connect().isEnabled()) {
						try {
							Thread.sleep(30);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					client.out.println(client.getMy_name().getText());
				} else if (line.startsWith("NAMEACCEPTED")) {
					client.getJTextArea_Main().append("you are connected successfully\n");
					client.getMessage_field().setEditable(true);
					client.getMy_name().setEditable(false);
					client.getIp_ad().setEditable(false);
					client.getJTextArea_Main().setEditable(false);
					client.getJToggleButton_showOnline().setEnabled(true);
					client.getJButton_disconnect().setEnabled(true);

				} else if (line.startsWith("MESSAGE")) {
					if ((line.substring(8).startsWith(client.getMy_name().getText()+":")) || (line.substring(line.indexOf(":") + 2).startsWith(client.getMy_name().getText()+","))
							|| ((line.charAt(line.indexOf(":") + 2) == ',')))
						client.getJTextArea_Main().append(line.substring(8, line.indexOf(":") + 2)
								+ line.substring(line.indexOf(",") + 1) + "\n");
				} else if (line.startsWith("SEND_NAMES")) {
					client.getJTextArea_Main().append(line.substring(10, line.length() - 2) + "\n");
				}else if (line.startsWith("MAXCLIENTS")) {
					socket.close();
					client.getJTextArea_Main().append("there are too many clients in the chat.\n");
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					client.dispose();
					break;
				}else if (line.startsWith("DISCONNECT")) {
					socket.close();
					client.getJTextArea_Main().append("disconnected succeeded! \n");
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					client.dispose();
					break;
				}else if (line.startsWith("ENABLE_SEND")) {
					client.getJButton_send().setEnabled(true);
				}else if (line.startsWith("ALL_DISCONNECT")) {
					client.getJTextArea_Main().append("the server is logging out. bye!\n");
					client.out.print("DISCONNECT_BY_SERVER");
				}else if (line.startsWith("NEW_CON")) {
					client.getJTextArea_Main().append(line.substring(7)+" is connected\n");
				}else if (line.startsWith("NEW_DIS")) {
					client.getJTextArea_Main().append(line.substring(7)+" is disconnected\n");
				}else if (line.startsWith("DIS_SEND")) {
					client.getJButton_send().setEnabled(false);
					client.getJTextArea_Main().append("wait for more clients to connect...\n");
				}else if (line.startsWith("NOT_ENOUGH_CLIENT")) {
					client.getJTextArea_Main().append("wait for more clients to connect...\n");
				}
			}
		}
		catch (Exception e) {

		}

}
}
