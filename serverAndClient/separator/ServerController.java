package separator;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import javafx.application.Platform;



public class ServerController
{

	ServerGUI gui;

	//Server Stuff
	ServerSocket serverSock;	//What clients connect to
	ArrayList<Socket> clientSocks;
	ArrayList<String> clientLabels;
	ServerThread2 serverThread;
	boolean isServer;
	
	//Constructor
	public ServerController()
	{
		gui = null;
		
		serverSock = null;//the 'server' that will wait for a client socket to connect
		clientSocks = new ArrayList<Socket>();
		clientLabels = new ArrayList<String>();
		serverThread = new ServerThread2(this);
		isServer = true;
	}
	
	String setupHost()
	{
		String hostIP = null;
		
		return hostIP;
	}
	
	void closeSocks(String state)
	{
		
	}
	
	
}
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class ServerThread2 extends Thread
{
	ServerController game;
	
	public ServerThread2(ServerController game)
	{
		this.game = game;
	}
	
	public void run()
	{
		//Check if the server socket is ok
		try
		{
			game.serverSock.setSoTimeout(1000);//sets time to wait for client to 1 second
		}
		catch(SocketException e)//thrown if the socket is bad
		{
			Platform.runLater(
							new Runnable(){
								@Override
								public void run(){
									game.gui.addressLabel.setText("Unable to establish host");
								}
							});
		}
		
		//How the controller works when in the "server" state
		while(game.gui.state.equals("server"))
		{
			try {
				Socket sock = game.serverSock.accept();
				if(!game.gui.state.equals("server")) return;
				/*DEBUG*/System.out.println("Client has connected");
				DataInputStream in = new DataInputStream(sock.getInputStream());
				String input = in.readUTF();
				game.clientLabels.add(input);
				game.clientSocks.add(sock);
				/*DEBUG*/System.out.println(input);
			}
			catch(SocketTimeoutException e){//no clients connect within the timeout delay
				//System.out.println("Nobody wanted to connect.");
				//That's fine, we'll just keep waiting
			}
			catch(IOException e){
				//System.out.println("IOException during accept()");
				//oh well, won't have that client
			}
			catch(NullPointerException e){
				//System.out.println("IOException during accept()");
				//oh well, won't have that client
			}
			
			
			//update GUI
			String names = "";
			for(int i = 0; i < game.clientSocks.size(); i++)
			{
				if(game.clientSocks.get(i).isClosed())
				{
					game.clientSocks.remove(i);
					game.clientLabels.remove(i);
					i--;
				}
				else
				{
					names = names+game.clientLabels.get(i)+"\n";
				}
			}
			
			for(int i = 0; i < game.clientSocks.size(); i++)
			{
				try {
					DataOutputStream out = new DataOutputStream(game.clientSocks.get(i).getOutputStream());
					out.writeUTF(names);
				}
				catch(IOException e) {}
			}
			
			//If the server is not in game mode, then do not proceed farther
			if(!game.gui.state.equals("game")) return;
			
			for(int i = 0; i < game.clientSocks.size(); i++) {
				try {
					DataOutputStream out = new DataOutputStream(game.clientSocks.get(i).getOutputStream());
					out.writeUTF("PLAY");
				}
				catch (IOException e) {}
			}
			
			int currentPlayer = 0;
			String move = "Game started!";
			
			while(game.gui.state.equals("game")) {
				
				for(int i = 0; i < game.clientSocks.size(); i++) {
					try {
						DataOutputStream out = new DataOutputStream(game.clientSocks.get(i).getOutputStream());
						out.writeUTF(move+";"+game.clientLabels.get(currentPlayer));
					}
					catch (IOException e) {}
				}
				
				try {
					DataInputStream in = new DataInputStream(game.clientSocks.get(currentPlayer).getInputStream());
					move = game.clientLabels.get(currentPlayer) + " played " + in.readUTF();
				}
				catch (IOException e) {
					move = game.clientLabels.get(currentPlayer) + " was skipped by server";
				}
				
				for(int i = 0; i < game.clientSocks.size(); i++) {
					try {
						DataOutputStream out = new DataOutputStream(game.clientSocks.get(i).getOutputStream());
						out.writeUTF(move);
					}
					catch (IOException e) {}
				}
				
				currentPlayer++;
				if(currentPlayer >= game.clientSocks.size()) currentPlayer = 0;
				
			}
		}
	}
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
