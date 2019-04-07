import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedList;

import javafx.application.Platform;

public class ClientController {
	
	ClientGUI gui;
	
	//server stuff
	ServerSocket serverSock;//the 'server' that will wait for a client socket to connect
	ArrayList<Socket> clientSocks;
	ArrayList<String> clientLabels;
	ServerThread serverThread;
	boolean isServer;
	
	//client stuff
	Socket thisSock; //the client
	ClientThread clientThread;
		
	public ClientController() {
		gui = null;
		
		serverSock = null;//the 'server' that will wait for a client socket to connect
		clientSocks = new ArrayList<Socket>();
		clientLabels = new ArrayList<String>();
		serverThread = new ServerThread(this);
		isServer = false;
		
		thisSock = null; //the client
		clientThread = new ClientThread(this);
	}
	
	
	String setupHost() {
		String hostIP = null;

		//create host
		boolean success = false;
		int attempts = 0;//keeps track of attempts to establish connection
		while(!success && attempts < 10){//tries ten times to create the server
			/*DEBUG*/System.out.println("Trying to make host");
			attempts++;
			try{
				serverSock = new ServerSocket(0);//throws IOException if port is taken
				success = true;
				hostIP = InetAddress.getLocalHost().getHostAddress() +":"+ serverSock.getLocalPort();
				
				//put ip in clipboard to make my life easier
				StringSelection data = new StringSelection(hostIP);
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				cb.setContents(data, data);
				
			}
			catch(IOException e){
				//System.out.println("Could not create ServerSocket at port "+port);
			}
		}
		
		if(!success) {
			hostIP = "Unable to establish host";
		}
		
		/*DEBUG*/System.out.println("Made host: "+hostIP);
		
		return hostIP;
	}
	
	String connectToHost(String addressStr, String name) {
		try {
			thisSock = new Socket();
			InetSocketAddress address = new InetSocketAddress(addressStr.split(":", 0)[0], Integer.parseInt(addressStr.split(":", 0)[1]));
			thisSock.connect(address, 5000);
			DataOutputStream out = new DataOutputStream(thisSock.getOutputStream());
			out.writeUTF(name);
			return "Connected!";
		}
		catch(UnknownHostException e){
			return "Could not find host!";
		}
		catch(NumberFormatException e){
			return "Invalid host address!";
		}
		catch(SocketTimeoutException e){
			return "Connection attemp timed out! Try again";
		}
		catch(IOException e){
			return "Error resolving host!";
		}
	}
	
	void closeSocks(String state){
		if(state.equals("hosting")) {
			try{
				serverSock.close();
			}
			catch(Exception e){}
			serverSock = null;//the 'server' that will wait for a client socket to connect
			for(Socket s:clientSocks) {
				try{
					s.close();
				}
				catch(Exception e){}
			}
			clientSocks = new ArrayList<Socket>();
			clientLabels = new ArrayList<String>();
		}
		else if(state.equals("lobby")) {
			try{
				thisSock.close();
			}
			catch(Exception e){}
			thisSock = null; //the client
		}
	}
	
	
	void writeToServer(String mes) throws IOException {
		DataOutputStream out = new DataOutputStream(thisSock.getOutputStream());
		out.writeUTF(mes);
	}
	
	
}//end controller


///////////////////////////////////////////////////////////////////////////////////////////////////
///////////////////////////////////////////////////////////////////////////////////////////////////

class ClientThread extends Thread{
	
	ClientController game;
	LinkedList<Card> tempList;
	String[] mess;
	
	public ClientThread(ClientController game) {
		this.game = game;
		tempList = new LinkedList<Card>();
		mess = null;
	}
	
	public void run() {
		
		/*DEBUG*/System.out.println(getId()+": Thread started");
		
		while(!game.gui.state.equals("main")) {
		
			try {
				DataInputStream in = new DataInputStream(game.thisSock.getInputStream());
				//*DEBUG*/System.out.println(getId()+": Wating for message from server");
				String mes = in.readUTF();
				//*DEBUG*/System.out.println(getId()+": got from server: "+mes);
				
				//Client State 1@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				if((game.gui.state.equals("lobby") || game.gui.state.equals("hosting")) && mes.equals("PLAY")){
					//update gui
					Platform.runLater(new Runnable() {
					   @Override
					   public void run() {
						   game.gui.game();
					   }
					});
				}
				//Client State 2@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				else if(game.gui.state.equals("lobby") || game.gui.state.equals("hosting")) {
					Platform.runLater(new Runnable() {
						   @Override
						   public void run() {
							   game.gui.infoLabel.setText(mes);;
						   }
						});
				}
				//Client State 3@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
				else if(game.gui.state.equals("game")){
					
					//&&&&&Part 1&&&&&
					//Check to see if we have a valid message that has semicolons
					if(mes.indexOf(';') == -1) continue;
					
					//Split the in message
					//Either
						//turn;game info;player whose turn it is
					//or
						//card;activeCards;inactiveCards
					/*String[]*/ mess = mes.split(";", 0);
					
					//&&&&Part 2&&&&&
					Platform.runLater(new Runnable() {
					   @Override
					   public void run() {
						   
						   //Update players messages about the previous move and whose turn it is
						   if(mess[0].equals("turn"))
						   {
							   //Tells the Client what move was made
							   game.gui.infoLabel.setText(mess[1]);
							   
							   //Determines if this Client is the one to go next
							   if(mess[2].equals(game.gui.yourName)) {
								   game.gui.turnLabel.setText("It's your turn");
								   game.gui.root.getChildren().add(game.gui.playButton);
							   }
							   else {
								   game.gui.turnLabel.setText("It's " + mess[2] + "'s turn");
							   }
						   }
						   //Update each Client GUI's list of cards
						   else if(mess[0].equals("cards"))
						   {
							   for(String card: mess[1].split(","))
								   tempList.add(new Card(card));
							   game.gui.yourCards.setActiveCards(tempList);
							   tempList.clear();
							   for(String card: mess[2].split(","))
								   tempList.add(new Card(card));
							   game.gui.yourCards.setInactiveCards(tempList);
							   tempList.clear();
							   
							   //Test if client got the message
							   game.gui.testLabel.setText(mes);
						   }
					   }
					});
				}
				
			} catch (IOException e) {
				
			}
		}
		/*DEBUG*/System.out.println(getId()+": Thread ended");
		
	}//end run
}//end ServerListener

class ServerThread extends Thread{
	
	ClientController game;
	
	public ServerThread(ClientController game) {
		this.game = game;
	}
	
	public void run() {
		
		try {
			game.serverSock.setSoTimeout(1000);//sets time to wait for client to 1 second
		}
		catch(SocketException e){//thrown if the socket is bad
			Platform.runLater(new Runnable() {
			    @Override
			    public void run() {
			    	game.gui.addressLabel.setText("Unable to establish host");
			    }
			});
		}
		//1st Stage@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		while(game.gui.state.equals("hosting")) {
			try {
				Socket sock = game.serverSock.accept();
				if(!game.gui.state.equals("hosting")) return;
				/*DEBUG*/System.out.println("Client Connected");
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
		
			//update gui
			String names = "";
	    	for(int i = 0; i < game.clientSocks.size(); i++) {
	    		if(game.clientSocks.get(i).isClosed()) {
	    			game.clientSocks.remove(i);
	    			game.clientLabels.remove(i);
	    			i--;
	    		}
	    		else {
	    			names = names+game.clientLabels.get(i)+"\n";
	    		}
	    	}
	    	
	    	for(int i = 0; i < game.clientSocks.size(); i++) {
				try {
					DataOutputStream out = new DataOutputStream(game.clientSocks.get(i).getOutputStream());
					out.writeUTF(names);
				}
				catch (IOException e) {}
			}
	    	
		}
		//2nd Stage@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		//Don't move past if this thread's client hasn't gone in game yet
		if(!game.gui.state.equals("game")) return;
		
		//3rd Stage@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		//Send the string "PLAY" to all of the clients
		for(int i = 0; i < game.clientSocks.size(); i++) {
			try {
				DataOutputStream out = new DataOutputStream(game.clientSocks.get(i).getOutputStream());
				out.writeUTF("PLAY");
			}
			catch (IOException e) {}
		}
		
		//Initial startup for the game@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		String move = "Game started!";
		
		//CREATE CARD GAME OBJECT
		CardGame cardGame = new CardGame(game.clientLabels.size(), game.clientLabels, game.clientSocks, new File("cardlist"));
		cardGame.assignDealear(game.clientLabels.get(0));
		PlayerQueue playerList = cardGame.sortPlayersInPlayOrder();
		Player focusPlayer;
		//4th Stage@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		//DEAL CARDS
		cardGame.dealCards();
		
		//TENTATIVELY
		//HAVE THE SERVER SEND LIST OF STRINGS TO PLAYERS FOR THEIR HAND OF CARDS
		
		while(game.gui.state.equals("game")) {
			//Get the player that goes next
			focusPlayer = playerList.nextPlayer();
			
			//Group 1@@@@@Send Card information to each player what cards they currently have@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			for(Player p: playerList) {
				try {
					DataOutputStream out = new DataOutputStream(p.getSock().getOutputStream());
					//The players card list uses 3 delimiters
					//The ';' delimits the active list form the inactive list. ActiveCards|InactiveCards
					//The ',' delimits the cards in a list from each other. Card1;Card2;Card3
					//The ' ' delimits the specifics of a card. CardValue CardCategory
					
					//Adding in an extra first group to notify what kind of message is sent
					//Add word "cards" to denote we are updating cards
					out.writeUTF("cards;"+p.getCardListForUTF());
				}
				catch (IOException e) {}
			}
			
			//Group 2@@@@@Message of what was the last move made and who goes next@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			for(int i = 0; i < game.clientSocks.size(); i++) {
				try {
					DataOutputStream out = new DataOutputStream(game.clientSocks.get(i).getOutputStream());
					//Adding in an extra first group to notify what kind of message is sent
					//Add word "turn" to denote we are updating labels about the move made/who goes next
					out.writeUTF("turn;"+move+";"+focusPlayer.getTeamName());
				}
				catch (IOException e) {}
			}
			//Group 3@@@@@Receive the player's move@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			try {
				DataInputStream in = new DataInputStream(focusPlayer.getSock().getInputStream());
				move = focusPlayer.getTeamName() + " played " + in.readUTF();
			}
			catch (IOException e) {
				move = focusPlayer.getTeamName() + " was skipped by server";
			}
			//???A good place to put Card game logic????
			//CHECK MOVE
			cardGame.checkMove(focusPlayer, move);
			
			
			//Group 4@@@@@Tells Everyone what move was made@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
			for(int i = 0; i < game.clientSocks.size(); i++) {
				try {
					DataOutputStream out = new DataOutputStream(game.clientSocks.get(i).getOutputStream());
					//Adding in an extra first group to notify what kind of message is sent
					//Add word "turn" to denote we are updating labels about the move made/who goes next
					out.writeUTF("turn;"+move);
				}
				catch (IOException e) {}
			}
			
			//CHECK FOR WIN CONDITION
			cardGame.checkWinCondition(focusPlayer, move);
			
		}
		
		
	}
}//end ServerListener
