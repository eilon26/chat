
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;


import javax.swing.GroupLayout;
import javax.swing.JButton;

/**
ChatCliant represent the Gui that every user get. 
he can enter his name(not empty,not name that is already exist) and enter the server ip address
then after pressing connect if there is more then 5 users he could
write the name of his destination member and to write the massage
after clicking on the send bottom he and his destination member will
see the massage on their screen. if the destination member is stay empty 
all the users will see the massage on their screen
on the screen every user can see massages from the server such as members connection or disconnection,not enaugh 
members in the chat, or the server close the chat.
@param jToggleButton_clear.the clear button will erase all the information on the client screen
@param jButton_disconnect. the Button disconnect will disconnect the client from the chat and close his window
@param jToggleButton_showOnline.the showOnline button will display on the screen all the chat members
@param in is a BufferedReader that the client can get massages from the server
@param out is PrintWriter that the client can send massages to the server
@author EILON and DANIEL

 */
public class ChatClient extends javax.swing.JFrame{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
     * initialization character streams for the socket.
     */
	BufferedReader in;
	PrintWriter out;
	/**
	 * all the variables initialization for the client Gui
	 */
	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JTextField dst;
	private javax.swing.JTextField ip_ad;
	private javax.swing.JButton jButton_send;
	private javax.swing.JButton jButton_disconnect;
	private javax.swing.JLabel jLabel_name;
	private javax.swing.JLabel jLabel_address;
	private javax.swing.JLabel jLabel_To;
	private javax.swing.JScrollPane jScrollPane1;
	private static javax.swing.JTextArea jTextArea_Main;
	private javax.swing.JToggleButton jToggleButton_connect;
	private javax.swing.JToggleButton jToggleButton_showOnline;
	private javax.swing.JToggleButton jToggleButton_clear;
	private javax.swing.JTextField message_field;
	private javax.swing.JTextField my_name;
	// End of variables declaration//GEN-END:variables

	/**
	 * Constructs the client by laying out the GUI
	 */
	public ChatClient() {
		initComponents();
	}
	/**
	 * laying out the client GUI 
	 */
	private void initComponents() {

		jToggleButton_connect = new javax.swing.JToggleButton();
		my_name = new javax.swing.JTextField();
		jLabel_name = new javax.swing.JLabel();
		jLabel_address = new javax.swing.JLabel();
		ip_ad = new javax.swing.JTextField();
		jToggleButton_showOnline = new javax.swing.JToggleButton();
		jToggleButton_clear = new javax.swing.JToggleButton();
		jScrollPane1 = new javax.swing.JScrollPane();
		jTextArea_Main = new javax.swing.JTextArea("");
		jLabel_To = new javax.swing.JLabel();
		dst = new javax.swing.JTextField();
		message_field = new javax.swing.JTextField("");
		jButton_send = new javax.swing.JButton();
		jButton_disconnect = new javax.swing.JButton();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

		jToggleButton_connect.setText("Connect");
		jToggleButton_connect.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				Connect(evt);
			}
		});
		jToggleButton_connect.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jToggleButton1ActionPerformed(evt);
			}
		});
		//this.name = getName1();
		my_name.setText("enter name");
		my_name.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				my_nameActionPerformed(evt);
			}
		});
		jLabel_name.setText("name:");
		jLabel_address.setText("address:");
		ip_ad.setText("127.0.0.1");
		ip_ad.setEditable(true);
		ip_ad.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				ip_adActionPerformed(evt);
			}
		});
		jToggleButton_showOnline.setText("Show online users");
		jToggleButton_showOnline.setEnabled(false);
		jToggleButton_showOnline.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				showonline(evt);
			}
		});
		jToggleButton_clear.setText("Clear");
		jToggleButton_clear.setEnabled(true);
		jToggleButton_clear.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseReleased(java.awt.event.MouseEvent evt) {
				Clear(evt);
			}
		});
		jToggleButton_clear.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				jToggleButton3ActionPerformed(evt);
			}
		});
		jTextArea_Main.setColumns(20);
		jTextArea_Main.setRows(5);
		jScrollPane1.setViewportView(jTextArea_Main);
		jTextArea_Main.setEditable(false);
		jLabel_To.setText("TO:");
		dst.setText("NAME");
		dst.setEditable(true);
		dst.setToolTipText("");
		message_field.setText("");
		message_field.setEditable(false);
		message_field.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				message_fieldActionPerformed(evt);
			}
		});
		jButton_send.setText("Send");
		jButton_send.setEnabled(false);
		jButton_send.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				Send(evt);
			}
		});
		jButton_disconnect.setText("Disconnect");
		jButton_disconnect.setEnabled(false);
		//jButton_disconnect.setToolTipText("");
		jButton_disconnect.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mousePressed(java.awt.event.MouseEvent evt) {
				Disconnect(evt);
			}
		});
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
		layout.setHorizontalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.LEADING)
								.addGroup(layout.createSequentialGroup()
										.addComponent(jLabel_To)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(dst, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(message_field, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(jButton_send))
								.addGroup(layout.createSequentialGroup()
										.addGap(17)
										.addGroup(layout.createParallelGroup(Alignment.LEADING)
												.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 459, GroupLayout.PREFERRED_SIZE)
												.addGroup(layout.createSequentialGroup()
														.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
																.addGroup(layout.createSequentialGroup()
																		.addComponent(jLabel_name)
																		.addPreferredGap(ComponentPlacement.UNRELATED)
																		.addComponent(my_name, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
																.addComponent(jToggleButton_connect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addGap(18)
														.addGroup(layout.createParallelGroup(Alignment.TRAILING, false)
																.addGroup(layout.createSequentialGroup()
																		.addComponent(jLabel_address)
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addComponent(ip_ad, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE))
																.addComponent(jButton_disconnect, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
														.addGap(18)
														.addGroup(layout.createParallelGroup(Alignment.LEADING)
																.addComponent(jToggleButton_showOnline, Alignment.TRAILING)
																.addComponent(jToggleButton_clear, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
										.addGap(97)))
						.addContainerGap())
				);
		layout.setVerticalGroup(
				layout.createParallelGroup(Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()
						.addContainerGap()
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jButton_disconnect, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
								.addComponent(jToggleButton_connect)
								.addComponent(jToggleButton_clear))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jToggleButton_showOnline)
								.addComponent(jLabel_name)
								.addComponent(my_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jLabel_address)
								.addComponent(ip_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(layout.createParallelGroup(Alignment.BASELINE)
								.addComponent(jLabel_To)
								.addComponent(dst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(message_field, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(jButton_send))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		getContentPane().setLayout(layout);
		pack();
	}


	private void jToggleButton1ActionPerformed(java.awt.event.ActionEvent evt) {
	}
	private void my_nameActionPerformed(java.awt.event.ActionEvent evt) {
	}
	private void ip_adActionPerformed(java.awt.event.ActionEvent evt) {
	}
	private void jToggleButton3ActionPerformed(java.awt.event.ActionEvent evt) {
	}
	private void Clear(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Clear
		jTextArea_Main.setText("");
	}
	private void showonline(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SHOWONLINE
		try {
		out.println("SEND_NAMES");
		}catch(java.lang.NullPointerException e) {
		}
	}
	private void Connect(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Connect
		jToggleButton_connect.setEnabled(false);
	}
	private void Send(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Send
        try {
		out.println(dst.getText()+","+message_field.getText());
        }catch(java.lang.NullPointerException e) {
		}
		message_field.setText("");
		dst.setText("");
	}
	private void Disconnect(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Disconnect
		out.println("DISCONNECT");
	}
	private void message_fieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_message_fieldActionPerformed
	}

	/**
	 * getters and setters for the variables of the client gui
	 */
	public javax.swing.JTextField getMessage_field(){
		return message_field;
	}
	public javax.swing.JTextField getMy_name(){
		return my_name;
	}
	public javax.swing.JToggleButton getJToggleButton_connect(){
		return jToggleButton_connect;
	}
	public javax.swing.JTextField getDst(){
		return dst;
	}
	public javax.swing.JTextArea getJTextArea_Main(){
		return jTextArea_Main;
	}
	public javax.swing.JButton getJButton_send(){
		return jButton_send;
	}
	public javax.swing.JTextField getIp_ad(){
		return ip_ad;
	}
	public javax.swing.JButton getJButton_disconnect(){
		return jButton_disconnect;
	}
	public javax.swing.JToggleButton getJToggleButton_showOnline(){
		return jToggleButton_showOnline;
	}
	/**
	 * end of getters and setters for the variables of the client gui
	 */
	/**
	 * Runs the client as an application with a closeable frame.
	 * when a new client is connected it open for him a socket
     * and start the clientThread that will take care that the client will get/send information
      * from/to the server by the agreed protocol
	 */
	public static void main(String[] args) throws Exception {
		ChatClient client = new ChatClient();//open the client gui
		client.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		client.setVisible(true);
		new clientThread(client).start();//start the thread that handle the client socket
	}
}