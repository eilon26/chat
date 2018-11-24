

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
/**
 * the server class represent the Gui that the chat manager use.
 * he can start and stop the chat. he can see every user that enter or exit the chat
 * he is start the listenerThread thread that waiting for user to connect to the server
 * and send it the suitable socket for the specific user.
 * @author EILON and DANIEL
 *
 */
public class server extends javax.swing.JFrame {
	/**
	 * The port that the server listens on.
	 */
	private static final int PORT = 9001;
	/**
	 * The set of all names of clients in the chat room. Maintained so that we can
	 * check that new clients are not registering name already in use.
	 */
	private static HashSet<String> names = new HashSet<String>();
	/**
	 * The set of all the print writers for all the clients. This set is kept so we
	 * can easily broadcast messages.
	 */
	private static HashSet<PrintWriter> writers = new HashSet<PrintWriter>();
    /**
     * if the amount of the chat members is beneath MIN_CLIENTS the chat members
     * could not send massage between each other 
     */
	private static int MIN_CLIENTS =5;
    /**
     * if the amount of the chat members is above MAX_CLIENTS the chat members
     * could not send massage between each other 
     */
	private static int MAX_CLIENTS =10;
	/**
	 * the server socket of the server
	 */
	private ServerSocket listener = null;
	/**
	 * the server gui
	 */
	private static server serverGui;
	/**
	 * all the variables initialization for the server Gui
	 */
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JButton jButton1;
	private javax.swing.JButton jButton2;
	private javax.swing.JLabel jLabel1;
	private javax.swing.JScrollPane jScrollPane1;
	private static javax.swing.JTextArea jTextArea1;
	// End of variables declaration//GEN-END:variables
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * getters and setters for the variables of the server gui
	 */
	public javax.swing.JTextArea getJTextArea1(){
		return jTextArea1;
	}
	public static int getPort() {
		return PORT;
	}

	public static HashSet<String> getNames() {
		return names;
	}

	public static HashSet<PrintWriter> getWriters() {
		return writers;
	}

	public static int getMinClients() {
		return MIN_CLIENTS;
	}
	public static void setMinClients(int m) {
		MIN_CLIENTS=m;
	}

	public static int getMaxClients() {
		return MAX_CLIENTS;
	}

	public static javax.swing.JTextArea getjTextArea1() {
		return jTextArea1;
	}
	/**
	 * end of getters and setters for the variables of the server gui
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * Constructs the server by laying out the GUI
	 */
	public server() {
		initComponents();
	}

	/**
	 * laying out the client GUI 
	 */
	private void initComponents() {
		jButton1 = new javax.swing.JButton();
		jButton1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		jButton2 = new javax.swing.JButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea1 = new javax.swing.JTextArea("");
		jLabel1 = new javax.swing.JLabel();
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		jButton1.setText("Start");
		jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				try {
					startServer(evt);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		jButton2.setText("Stop");
		jButton2.setEnabled(false);
		jButton2.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				StopServer(evt);
			}
		});
		jTextArea1.setColumns(20);
		jTextArea1.setRows(5);
		jScrollPane1.setViewportView(jTextArea1);
		jLabel1.setText("Server");
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addGap(18, 18, 18)
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
								.addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 360,
										javax.swing.GroupLayout.PREFERRED_SIZE)
								.addGroup(layout.createSequentialGroup().addComponent(jButton1)
										.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED,
												javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(jLabel1).addGap(107, 107, 107).addComponent(jButton2)))
						.addContainerGap(22, Short.MAX_VALUE)));
		layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup().addContainerGap()
						.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
								.addComponent(jButton1).addComponent(jButton2).addComponent(jLabel1))
						.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 249, Short.MAX_VALUE)
						.addContainerGap()));
		pack();
	}
	/**
	 * the function for the "start" button. after pressing the function open a new port for
	 * the server and start the listenerThread thread. that wait to handle the new member
	 * that will connect.
	 * 
	 * @param evt
	 * @throws IOException
	 */
	private void startServer(java.awt.event.MouseEvent evt) throws IOException {// GEN-FIRST:event_startServer
		jButton2.setEnabled(true);
		jButton1.setEnabled(false);
		listener = new ServerSocket(PORT);
		jTextArea1.append("The chat server is running.\n");
		new listenerThread(this.serverGui,this.listener).start();
	}
	/**
	 * the function for the "stop" button. after pressing the function annonce to all the members
	 *  that are connect that the server is closing. than disconnect them. and then close the server port.
	 * @param evt
	 */
	private void StopServer(java.awt.event.MouseEvent evt) {// GEN-FIRST:event_StopServer
		jButton2.setEnabled(false);
		jButton1.setEnabled(true);
		try {
			jTextArea1.append("stoping is in proccess...\n");
			for (PrintWriter writer : writers) { 
				writer.println("DISCONNECT_ALL");
			}
			Thread.sleep(6000);
			listener.close();
			jTextArea1.append("The chat server is stopped.\n");
			//dispose();
		} catch (IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		catch (java.util.ConcurrentModificationException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Runs the server as an application with "start" and "stop" button
	 * and area where the server manager can see the important 
	 * Occurrences in the chat.
	 */
	public static void main(String args[]) {
		try { 
			for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
				if ("Nimbus".equals(info.getName())) {
					javax.swing.UIManager.setLookAndFeel(info.getClassName());
					break;
				}
			}
		} catch (ClassNotFoundException ex) {
			java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (InstantiationException ex) {
			java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (IllegalAccessException ex) {
			java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		} catch (javax.swing.UnsupportedLookAndFeelException ex) {
			java.util.logging.Logger.getLogger(server.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
		}
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				serverGui = new server();
				serverGui.setVisible(true);

			}
		});
	}
}
