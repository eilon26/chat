
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * A serverThread thread class. serverThreads are spawned from the listening loop inside listenerThread
 *  and are responsible for a dealing with a single client. broadcasting massages for the client
 *   and getting its messages by the agreed protocol. more over it analyze the information that it get 
 *   from the clients and represent it on the server screen if needed.
 */
public class serverThread extends Thread {
	private String name;
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
	private server serverGui;
	//private ServerSocket listener;
	/**
	 * Constructs a serverThread thread. is get a socket and serverGui
	 * @param socket a Socket type
     * @param serverGui a server type
	 */
	public serverThread(Socket socket, server serverGui) {
		this.socket = socket;
		this.serverGui = serverGui;
	}
	/**
	 * requesting a screen name until a unique one has been submitted(not an empty one), 
	 * then send confirm massage to the client for the name. it registers the
	 *  name of the client and the client's output stream in a global set, then repeatedly gets inputs and
	 * send broadcasts from/to the client by the agreed protocol
	 */
	@Override
	public void run() {
		try {
			// Create character streams for the socket.
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
			if (serverGui.getNames().size()>=serverGui.getMaxClients()) {
				out.println("MAXCLIENTS");
				try {
					socket.close();//close the socket and then the serverThread thread is over!
				} catch (IOException e) {
				}
			}
			// Request a name from this client. Keep requesting until
			// a name is submitted that is not already used. Note that
			// checking for the existence of a name and adding the name
			// must be done while locking the set of erverGui.getNames().
			else {
				boolean flage = true;
				while (true) {
					if (flage)
						out.println("SUBMITNAME");
					else
						out.println("RESUBMITNAME");
					name = in.readLine();
					if (name == null) {
						return;
					} else if (name.equals("")||(name.charAt(0)==' ')||((name.charAt(name.length()-1)==' '))||name.equals("SEND_NAMES")) {
						flage = false;
					} else {
						synchronized (serverGui.getNames()) {
							if (!serverGui.getNames().contains(name)) {
								serverGui.getNames().add(name);
								break;
							} else
								flage = false;
						}
					}
				}
				serverGui.getJTextArea1().append("client "+ name+" is connected succeddfully\n");
				for (PrintWriter writer : serverGui.getWriters()) { 
					writer.println("NEW_CON"+name);
				}
				out.println("NAMEACCEPTED");
				serverGui.getWriters().add(out);
				if (serverGui.getNames().size()==serverGui.getMinClients()) {
					for (PrintWriter writer : serverGui.getWriters()) { 
						writer.println("ENABLE_SEND");
					}
				} else if (serverGui.getNames().size()>serverGui.getMinClients()) {
					out.println("ENABLE_SEND");
				}else out.println("NOT_ENOUGH_CLIENT");
				// Accept messages from this client and broadcast them.
				// Ignore other clients that cannot be broadcasted to.
				while (true) {
					String input = in.readLine();
					if (input == null) {//case empty massage
						return;
					}
					if(input.startsWith("DISCONNECT")) {//case of client disconnected
						serverGui.getNames().remove(name);
						serverGui.getWriters().remove(out);
						out.println("DISCONNECT");
						for (PrintWriter writer : serverGui.getWriters()) { 
							writer.println("NEW_DIS"+name);
						}
						serverGui.getJTextArea1().append("client "+ name+" is disconnected\n");
						if (serverGui.getNames().size()<serverGui.getMinClients()) {
							for (PrintWriter writer : serverGui.getWriters()) { 
								writer.println("DIS_SEND");
							}
						}
						try {
							socket.close();
						} catch (IOException e) {
						}
						break;
					}else if(input.startsWith("DISCONNECT_BY_SERVER")) {//case of client disconnected
						serverGui.getNames().remove(name);
						serverGui.getWriters().remove(out);
						out.println("DISCONNECT");
						serverGui.getJTextArea1().append("client "+ name+" is disconnected\n");
						try {
							socket.close();
						} catch (IOException e) {
						}
						break;
					}else if(input.startsWith("SEND_NAMES")){//case show how is connected
						if (serverGui.getWriters().contains(out)) {
							String ShowOnline="SEND_NAMESthe online members are: ";
							for (String currName : serverGui.getNames()) {
								ShowOnline+=currName+", ";
							}
							out.println(ShowOnline);
						}
					}
					else if (input.startsWith("RESET")) {
						serverGui.getNames().remove(name);
						serverGui.getWriters().remove(out);
					}
					else if ((input.indexOf(",")!=input.length()-1)&&(serverGui.getNames().size()>=serverGui.getMinClients())) {// //case regular massage and there is an actual massage
						for (PrintWriter writer : serverGui.getWriters()) { 
							writer.println("MESSAGE " + name + ": " + input);
						}
					}
				}
			}
		} catch (IOException e) {
			System.out.println(e);
		} finally {
			// This client is going down! Remove its name and its print
			// writer from the sets, and close its socket.
			if (name != null) {
				serverGui.getNames().remove(name);
			}
			if (out != null) {
				serverGui.getWriters().remove(out);
			}
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}
}

