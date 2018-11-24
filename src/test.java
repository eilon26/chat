
import static org.junit.jupiter.api.Assertions.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

import javax.swing.JFrame;

import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

//import chat2.server.Handler;


class test {

	//**************variable****************
	private static server server;
	private static ChatClient client1;
	private static ChatClient client2;
	private static ServerSocket listener;
	//***********************************************************************

	//private static server server;
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	
//	@Test
//	void testServerConnection() throws IOException {
//*************open the server gui**********************
		server = new server();
		server.setVisible(true);
        server.setMinClients(2);
        
//*****click start server**********************************	
		listener = new ServerSocket(server.getPort());
		
		server.getJTextArea1().append("The chat server is running.\n");
		new listenerThread(server,listener).start();
//*************start first client******************************
        client1 = new ChatClient();
        client1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client1.setVisible(true);
        new clientThread(client1).start();
        client1.getMy_name().setText("daniel");
        client1.getJToggleButton_connect().setEnabled(false);//click on connect
        Thread.sleep(2000);
//*************start second client******************************
        client2 = new ChatClient();
        client2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        client2.setVisible(true);
        new clientThread(client2).start();
        client2.getMy_name().setText("eilon");
        client2.getJToggleButton_connect().setEnabled(false);//click on connect
        Thread.sleep(2000);

//*********send massage between client2 to client4*************
        client1.getMessage_field().setText("hey");
        client1.getDst().setText("eilon");
        client2.getJTextArea_Main().setText("");
      //client2 click on send
		client1.out.println(client1.getDst().getText()+","+client1.getMessage_field().getText());
	}
	
	@Test
	void testServerConnection() throws IOException {
		assertFalse(listener.isClosed());
	}
	
		
	@Test
	void sendMassageBetweenClient() throws IOException {	
		assertTrue(client1.getJTextArea_Main().getText().startsWith("daniel: hey"));
	}
	@Test
	void onlineClients() throws IOException {	
		assertEquals(""+server.getNames(),"[daniel, eilon]");
	}
	@Test
	void testServerDisConnection() throws IOException {
		listener.close();
		assertTrue(listener.isClosed());
	}

}
