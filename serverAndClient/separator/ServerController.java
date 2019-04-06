package separator;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerController
{

	ServerGUI gui;

	//Server Stuff
	ServerSocket serverSock;	//What clients connect to
	ArrayList<Socket> clientSocks;
	ArrayList<String> clientLabels;
	ClientCatcher clientCatcher;
	Broadcaster broadcaster;
	boolean isServer;
	
	//Constructor
	public ServerController()
	{
		gui = null;
		
		serverSock = null;//the 'server' that will wait for a client socket to connect
		clientSocks = new ArrayList<Socket>();
		clientLabels = new ArrayList<String>();
		clientCatcher = new ClientCatcher(this);
		broadcaster = new Broadcaster(this);
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
class ClientCatcher extends Thread
{

	public ClientCatcher(ServerController serverController) {
		// TODO Auto-generated constructor stub
	}
	
}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
class Broadcaster extends Thread
{
	
}